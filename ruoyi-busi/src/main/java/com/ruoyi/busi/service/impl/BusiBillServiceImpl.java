package com.ruoyi.busi.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.google.common.collect.Maps;
import com.ruoyi.busi.component.alipay.config.Configs;
import com.ruoyi.busi.service.IBusiBookBorrowService;
import com.ruoyi.busi.service.IBusiBookPreborrowService;
import com.ruoyi.busi.service.TradeService;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.busi.mapper.BusiBillMapper;
import com.ruoyi.busi.domain.BusiBill;
import com.ruoyi.busi.service.IBusiBillService;
import com.ruoyi.common.core.text.Convert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 账单管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-03-29
 */
@Service
public class BusiBillServiceImpl implements IBusiBillService 
{
    @Autowired
    private BusiBillMapper busiBillMapper;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IBusiBookPreborrowService busiBookPreborrowService;
    @Autowired
    private IBusiBookBorrowService busiBookBorrowService;

    @Autowired
    private TradeService tradeService;

    /**
     * 查询账单管理
     * 
     * @param id 账单管理ID
     * @return 账单管理
     */
    @Override
    public BusiBill selectBusiBillById(Long id)
    {
        return busiBillMapper.selectBusiBillById(id);
    }

    /**
     * 查询账单管理列表
     * 
     * @param busiBill 账单管理
     * @return 账单管理
     */
    @Override
    public List<BusiBill> selectBusiBillList(BusiBill busiBill)
    {
        if(!ShiroUtils.getSysUser().hasRole("manager")){
            busiBill.setUserId(ShiroUtils.getUserId());
        }
        return busiBillMapper.selectBusiBillList(busiBill);
    }

    /**
     * 新增账单管理
     * 
     * @param busiBill 账单管理
     * @return 结果
     */
    @Override
    public Long insertBusiBill(BusiBill busiBill)
    {
        busiBill.setCreateTime(DateUtils.getNowDate());
        return busiBillMapper.insertBusiBill(busiBill);
    }

    /**
     * 修改账单管理
     * 
     * @param busiBill 账单管理
     * @return 结果
     */
    @Override
    public int updateBusiBill(BusiBill busiBill)
    {
        return busiBillMapper.updateBusiBill(busiBill);
    }

    /**
     * 删除账单管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBusiBillByIds(String ids)
    {
        return busiBillMapper.deleteBusiBillByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除账单管理信息
     * 
     * @param id 账单管理ID
     * @return 结果
     */
    @Override
    public int deleteBusiBillById(Long id)
    {
        return busiBillMapper.deleteBusiBillById(id);
    }

   public Map<String,String> paySuccess(HttpServletRequest request,
                             HttpServletResponse response) throws AlipayApiException {
       int payType=1;
       if(payType > 2 || payType < 0){
           throw new IllegalArgumentException("支付类型不正确，平台目前仅支持支付宝与微信支付");
       }
       Map<String,String> param = Maps.newHashMap();
       if(payType == 1){//支付宝支付
           //1、获取request里所有与alipay相关的参数，封装成一个map
           Enumeration<String> parameterNames = request.getParameterNames();
           while (parameterNames.hasMoreElements()){
               String parameterName = parameterNames.nextElement();
               if(!parameterName.toLowerCase().equals("sign_type")){
                   param.put(parameterName,request.getParameter(parameterName));
               }
           }

           // 2、验证请求是否是alipay返回的请求内容【验证请求合法性】
           // 很重要
           boolean isPassed = AlipaySignature.rsaCheckV2(param, Configs.getAlipayPublicKey(),"utf-8",Configs.getSignType());
           param.put("isPassed",isPassed+"");
           return param;

       }else if(payType == 2){//微信支付
            return param;
       }
       return param;
   }

    /**
     * 押金二维码
     * @param busiBill
     * @return
     */
    @Override
    public AjaxResult payDepositQrCode(BusiBill busiBill) {

        SysUser sysUser = sysUserService.selectUserById(ShiroUtils.getUserId());
        if(sysUser.getDeposit()>0){
            return AjaxResult.error("已缴纳押金，无需重复缴纳");
        }
        if(busiBill.getUserId()==null){
            busiBill.setUserId(ShiroUtils.getUserId());
        }
        busiBill.setMoney(new BigDecimal(100));
        busiBill.setBorrowId(sysUser.getUserId());
        busiBill.setBookName("押金");
        busiBill.setPayMessage("社区图书馆缴纳押金100元");
        busiBill.setBillSign(0);
        busiBill.setBillType(0);
        busiBill.setOperator(ShiroUtils.getUserId());
        busiBill.setCreateTime(DateUtils.getNowDate());
        busiBill.setOperator(ShiroUtils.getUserId());
        //返回订单号
//        Long billId = busiBillService.insertBusiBill(busiBill);
//        busiBill.setId(billId);
//        busiBill.setUserId();
        //插入未支付订单，生成二维码，返回二维码路径
        insertBusiBill(busiBill);
        AjaxResult ajaxResult = tradeService.tradeQrCode(busiBill);
        if(ajaxResult.get(AjaxResult.CODE_TAG)==AjaxResult.Type.SUCCESS){

        }
        return ajaxResult;
    }

    @Override
    public void payDepositSuccess(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException {
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, String> param = paySuccess(request, response);
        if(param.get("isPassed")!=null){
            Long billId = Long.parseLong(param.get("out_trade_no"));
            BusiBill busiBill =selectBusiBillById(billId);
            busiBill.setBillSign(1);
            busiBill.setPayTime(DateUtils.getNowDate());
            int count= updateBusiBill(busiBill);
            SysUser sysUser = sysUserService.selectUserById(busiBill.getBorrowId());
            sysUser.setDeposit(billId);
            sysUserService.updateUser(sysUser);
            if(count > 0){
//                    log.info("支付成功，订单完成支付");
                out.print("success");
            }else{
//                    log.info("支付失败，订单未能完成支付");
                out.print("unSuccess");
            }
        }else{
//                log.info("支付失败，订单未能完成支付");
            out.print("unSuccess");
        }
    }

    @Override
    public AjaxResult reDeposit(BusiBill busiBill) {
        return null;
    }

    @Override
    public AjaxResult payPenaltyQrCode(BusiBill busiBill) {
        SysUser sysUser = sysUserService.selectUserById(ShiroUtils.getUserId());
        busiBill=selectBusiBillById(busiBill.getId());
        if(busiBill.getBillSign()!=0){
            return AjaxResult.error("已缴纳押金，无需重复缴纳");
        }
        busiBill.setPayMessage("社区图书馆罚金押金"+busiBill.getMoney()+"元");
        //返回订单号
//        Long billId = busiBillService.insertBusiBill(busiBill);
//        busiBill.setId(billId);
//        busiBill.setUserId();
        AjaxResult ajaxResult = tradeService.tradeQrCode(busiBill);
        if(ajaxResult.get(AjaxResult.CODE_TAG)==AjaxResult.Type.SUCCESS){

        }
        return ajaxResult;
    }

    @Override
    public void payPenaltySuccess(HttpServletRequest request, HttpServletResponse response) {

    }

    @Override
    public AjaxResult rePenalty(BusiBill busiBill) {
        return null;
    }
}
