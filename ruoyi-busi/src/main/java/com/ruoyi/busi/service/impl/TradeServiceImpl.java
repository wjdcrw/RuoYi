package com.ruoyi.busi.service.impl;

import com.alipay.api.AlipayResponse;
import com.alipay.api.domain.TradeFundBill;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.ruoyi.busi.component.alipay.TradePayProp;
import com.ruoyi.busi.component.alipay.config.Configs;
import com.ruoyi.busi.component.alipay.model.ExtendParams;
import com.ruoyi.busi.component.alipay.model.GoodsDetail;
import com.ruoyi.busi.component.alipay.model.builder.AlipayTradePrecreateRequestBuilder;
import com.ruoyi.busi.component.alipay.model.builder.AlipayTradeQueryRequestBuilder;
import com.ruoyi.busi.component.alipay.model.builder.AlipayTradeRefundRequestBuilder;
import com.ruoyi.busi.component.alipay.model.result.AlipayF2FPrecreateResult;
import com.ruoyi.busi.component.alipay.model.result.AlipayF2FQueryResult;
import com.ruoyi.busi.component.alipay.model.result.AlipayF2FRefundResult;
import com.ruoyi.busi.component.alipay.service.AlipayTradeService;
import com.ruoyi.busi.component.alipay.service.impl.AlipayTradeServiceImpl;
import com.ruoyi.busi.component.alipay.utils.Utils;
import com.ruoyi.busi.component.alipay.utils.ZxingUtils;
import com.ruoyi.busi.domain.BusiBill;
import com.ruoyi.busi.service.IBusiBillService;
import com.ruoyi.busi.service.TradeService;
/*import com.tuling.tulingmall.dao.PortalOrderDao;
import com.tuling.tulingmall.domain.OmsOrderDetail;
import com.tuling.tulingmall.mapper.OmsOrderMapper;
import com.tuling.tulingmall.model.OmsOrder;
import com.tuling.tulingmall.model.OmsOrderItem;*/
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hpsf.Decimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：dcr
 * @description: 交易服务
 **/
@Slf4j
@Service
public class TradeServiceImpl implements TradeService {
    private AtomicInteger atomicInteger=new AtomicInteger();
    @Autowired
    private TradePayProp tradePayProp;

    // 支付宝当面付2.0服务
    private static AlipayTradeService tradeService;


    @Autowired
    private IBusiBillService busiBillService;
    @Autowired
    private ISysUserService sysUserService;

    static {
        /**
         * 支付宝的初始化
         *
         * 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();
    }

    /**
     * 根据订单生成支付二维码
     *
     * @param
     * @param
     * @return
     */
    @Override
    public AjaxResult tradeQrCode(BusiBill busiBill) {
        try {
//            busiBillService.insertBusiBill(busiBill);
            String path = aliPayQrCode(busiBill);
            return AjaxResult.success(path);

        } catch (Exception e) {
            log.error("生成支付二维码异常:{}",e.getMessage(),e.getCause());
            throw new RuntimeException("生成支付二维码异常：请联系管理员！");
        }
    }

    /**
     * 查询订单支付状态
     *
     * @param orderId
     *          订单编号
     * @param payType
     *
     * @return
     */
    @Override
    public AjaxResult tradeStatusQuery(Long orderId, Integer payType) {
//        OmsOrderDetail orderDetail = portalOrderDao.getDetail(orderId);
        BusiBill busiBill=busiBillService.selectBusiBillById(orderId);
        if(payType == 1){//支付宝支付
            AjaxResult result = alipayTradeQuery(orderId.toString());
            if(result.get(AjaxResult.CODE_TAG) == AjaxResult.Type.SUCCESS){
                //订单查询详情信息
                AlipayTradeQueryResponse response = (AlipayTradeQueryResponse) result.get(AjaxResult.DATA_TAG);

                /*
                 * 支付详情处理逻辑，根据需要处理
                 */
                //更新新订单支付状态
                if(busiBill.getBillSign() == 0){
                    busiBill.setBillSign(2);
                    busiBillService.updateBusiBill(busiBill);
                }
                return AjaxResult.success("支付成功!");
            }
        }else{//微信支付

        }
        return AjaxResult.error();
    }

    private AjaxResult alipayTradeQuery(String orderSn){
        // (必填) 商户订单号，通过此商户订单号查询当面付的交易状态
        String outTradeNo = orderSn;

        // 创建查询请求builder，设置请求参数
        AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder()
                .setOutTradeNo(outTradeNo);

        AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("查询返回该订单支付成功: )");
                AlipayTradeQueryResponse response = result.getResponse();
                dumpResponse(response);
                log.info(response.getTradeStatus());
                if (Utils.isListNotEmpty(response.getFundBillList())) {
                    for (TradeFundBill bill : response.getFundBillList()) {
                        log.info(bill.getFundChannel() + ":" + bill.getAmount());
                    }
                }
                return AjaxResult.success(response);
            case FAILED:
                log.error("查询返回该订单支付失败或被关闭!!!");
                break;

            case UNKNOWN:
                log.error("系统异常，订单支付状态未知!!!");
                break;

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
        return AjaxResult.error();
    }

    /**
     * 支付宝方式
     * @return
     */
    private String aliPayQrCode(BusiBill busiBill){
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        String outTradeNo = busiBill.getId()+"";

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费” todo 待完善
        /*if(CollectionUtils.isEmpty(orderDetail.getOrderItemList())){
            throw new IllegalArgumentException("空订单,不允许支付");
        }*/
        /*OmsOrderItem omsOrderItem = orderDetail.getOrderItemList().get(0);
        String subject = omsOrderItem.getProductBrand() + "品牌"
                + omsOrderItem.getProductName()+"产品购买扫码消费";*/
        String subject= "社区图书馆--"+busiBill.getBookName()+"支付";
        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = busiBill.getMoney().toString();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元" todo 待完善
        /*String body = "购买商品"+orderDetail.getOrderItemList().size()
                +"件共"+totalAmount+"元";*/
        String body ="支付"+busiBill.getMoney().toString()+"元";
        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = ShiroUtils.getSysUser().getUserId().toString();

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "bookstore";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "120m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<GoodsDetail>();
      /*  orderDetail.getOrderItemList().stream().forEach((item)->{
            GoodsDetail goods = GoodsDetail.newInstance(item.getProductId()+"",
                    item.getProductName(),
                    //将金额单位[元]转为[分]
                    item.getProductPrice().multiply(new BigDecimal(100)).longValue(),
                    item.getProductQuantity());
            // 创建好一个商品后添加至商品明细列表
            GoodsDetail goods = GoodsDetail.newInstance(busiBill.getBookId(),
                    busiBill.getBookName(),
                    busiBill.getMoney().multiply(new BigDecimal(100)).longValue(),
                    1
                    );
            goodsDetailList.add(goods);
        });*/
        // 创建好一个商品后添加至商品明细列表
        GoodsDetail goods = GoodsDetail.newInstance(busiBill.getBookId().toString(),
                busiBill.getBookName(),
                busiBill.getMoney().multiply(new BigDecimal(100)).longValue(),
                1
        );
        goodsDetailList.add(goods);
        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setSellerId(sellerId).setBody(body)
                .setUndiscountableAmount(undiscountableAmount)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                .setNotifyUrl(tradePayProp.getPaySuccessCallBack())//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置 1代表支付宝支付
                .setGoodsDetailList(goodsDetailList);
        log.info("alipay callback url:--->"+builder.getNotifyUrl());
        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: ");
                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);
                // 需要修改为运行机器上的路径
                String filePath = String.format(tradePayProp.getAliPayPath() + "/qr-%s.png",
                        Timestamp.valueOf(LocalDateTime.now()).getTime()+
                                atomicInteger.getAndDecrement()+response.getOutTradeNo());
                log.info("filePath:" + tradePayProp.getStorePath()+ filePath);
                //写到图片当中
                ZxingUtils.getQRCodeImge(response.getQrCode(), 256, tradePayProp.getStorePath()+filePath);
                return tradePayProp.getHttpBasePath() + filePath;
            case FAILED:
                log.error("支付宝预下单失败!!!");
                break;
            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                break;
            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                break;
        }
        return null;
    }

    /**
     * 退押金
     * @param busiBill
     * @return
     */
    public AjaxResult test_trade_refund(BusiBill busiBill) {
        // (必填) 外部订单号，需要退款交易的商户外部订单号
        String outTradeNo = busiBill.getId().toString();

        // (必填) 退款金额，该金额必须小于等于订单的支付金额，单位为元
        String refundAmount = busiBill.getMoney().toString();

        // (可选，需要支持重复退货时必填) 商户退款请求号，相同支付宝交易号下的不同退款请求号对应同一笔交易的不同退款申请，
        // 对于相同支付宝交易号下多笔相同商户退款请求号的退款交易，支付宝只会进行一次退款
        String outRequestNo = "";

        // (必填) 退款原因，可以说明用户退款原因，方便为商家后台提供统计
        String refundReason = "押金退款";

        // (必填) 商户门店编号，退款情况下可以为商家后台提供退款权限判定和统计等作用，详询支付宝技术支持
        String storeId = "bookstore";

        // 创建退款请求builder，设置请求参数
        AlipayTradeRefundRequestBuilder builder = new AlipayTradeRefundRequestBuilder()
                .setOutTradeNo(outTradeNo).setRefundAmount(refundAmount).setRefundReason(refundReason)
                .setOutRequestNo(outRequestNo).setStoreId(storeId);

        AlipayF2FRefundResult result = tradeService.tradeRefund(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝退款成功");

                return AjaxResult.success("支付宝退款成功 ");
            case FAILED:
                log.error("支付宝退款失败!!!");
                return AjaxResult.error("支付宝退款失败!!!");

            case UNKNOWN:
                log.error("系统异常，订单退款状态未知!!!");
                return AjaxResult.error("系统异常，订单退款状态未知!!!");

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                return AjaxResult.error("不支持的交易状态，交易返回异常!!!");
        }
    }

    // 简单打印应答
    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }



    /**
     * 微信支付方式
     * @return
     */
    private String wechatPayQrCode(BusiBill busiBill){
        return null;
    }
}
