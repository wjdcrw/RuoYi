package com.ruoyi.web.controller.busi;

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
import com.ruoyi.busi.component.alipay.service.AlipayTradeService;
import com.ruoyi.busi.domain.BusiBookBorrow;
import com.ruoyi.busi.domain.BusiBookPreborrow;
import com.ruoyi.busi.service.IBusiBookBorrowService;
import com.ruoyi.busi.service.IBusiBookPreborrowService;
import com.ruoyi.busi.service.TradeService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.DictUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.busi.domain.BusiBill;
import com.ruoyi.busi.service.IBusiBillService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 账单管理Controller
 * 
 * @author ruoyi
 * @date 2021-04-11
 */
@Controller
@RequestMapping("/busi/bill")
public class BusiBillController extends BaseController
{
    private String prefix = "busi/bill";

    @Autowired
    private IBusiBillService busiBillService;
    @Autowired
    private ISysUserService iSysUserService;
    @Autowired
    private IBusiBookPreborrowService busiBookPreborrowService;
    @Autowired
    private IBusiBookBorrowService busiBookBorrowService;

    @Autowired
    private TradeService tradeService;

    @RequiresPermissions("busi:bill:view")
    @GetMapping()
    public String bill()
    {
        return prefix + "/bill";
    }

    /**
     * 查询账单管理列表
     */
    @RequiresPermissions("busi:bill:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BusiBill busiBill)
    {
        startPage();
        List<BusiBill> list = busiBillService.selectBusiBillList(busiBill);
        return getDataTable(list);
    }

    /**
     * 导出账单管理列表
     */
    @RequiresPermissions("busi:bill:export")
    @Log(title = "账单管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BusiBill busiBill)
    {
        List<BusiBill> list = busiBillService.selectBusiBillList(busiBill);
        ExcelUtil<BusiBill> util = new ExcelUtil<BusiBill>(BusiBill.class);
        return util.exportExcel(list, "bill");
    }

    /**
     * 新增账单管理
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存账单管理
     */
    @RequiresPermissions("busi:bill:add")
    @Log(title = "账单管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BusiBill busiBill)
    {
        return toAjax(busiBillService.insertBusiBill(busiBill).intValue());
    }

    /**
     * 修改账单管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BusiBill busiBill = busiBillService.selectBusiBillById(id);
        mmap.put("busiBill", busiBill);
        return prefix + "/edit";
    }

    /**
     * 修改保存账单管理
     */
    @RequiresPermissions("busi:bill:edit")
    @Log(title = "账单管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BusiBill busiBill)
    {
        return toAjax(busiBillService.updateBusiBill(busiBill));
    }

    /**
     * 删除账单管理
     */
    @RequiresPermissions("busi:bill:remove")
    @Log(title = "账单管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(busiBillService.deleteBusiBillByIds(ids));
    }


    @GetMapping("/payDepositQrCode")
    public String payDepositQrCode()
    {
        return prefix + "/qrCode";
    }
    /**
     * 获取缴纳押金的二维码
     * @return
     */
    @Log(title = "押金二维码", businessType = BusinessType.INSERT)
    @PostMapping( "/payDepositQrCode")
    @ResponseBody
    public AjaxResult payDepositQrCode(BusiBill busiBill, ModelMap mmap){
        SysUser sysUser = iSysUserService.selectUserById(ShiroUtils.getUserId());
        if(sysUser.getDeposit()>0){
            return AjaxResult.error("已缴纳押金，无需重复缴纳");
        }
        if(busiBill.getUserId()==null){
            busiBill.setUserId(ShiroUtils.getUserId());
        }
        busiBill.setMoney(new BigDecimal(100));
        busiBill.setBookId(-1L);
        busiBill.setBookName("押金");
        busiBill.setBillSign(0);
        busiBill.setBillType(0);
        busiBill.setCreateTime(DateUtils.getNowDate());
        busiBill.setOperator(ShiroUtils.getUserId());
        //返回订单号
//        Long billId = busiBillService.insertBusiBill(busiBill);
//        busiBill.setId(billId);
//        busiBill.setUserId();
        //插入未支付订单，生成二维码，返回二维码路径
        AjaxResult ajaxResult = tradeService.tradeQrCode(busiBill);
        return ajaxResult;
    }

    @ApiOperation("支付成功的回调")
    @ApiImplicitParams({@ApiImplicitParam(name = "payType", value = "支付方式:0->未支付,1->支付宝支付,2->微信支付",
            allowableValues = "1,2", paramType = "query", dataType = "integer")})
    @Log(title = "支付成功", businessType = BusinessType.INSERT)
    @RequestMapping(value = "/paySuccess",method = RequestMethod.POST)
    @ResponseBody
    public void paySuccess(
                           HttpServletRequest request,
                           HttpServletResponse response) throws AlipayApiException {
        int payType=1;
        if(payType > 2 || payType < 0){
            throw new IllegalArgumentException("支付类型不正确，平台目前仅支持支付宝与微信支付");
        }
        if(payType == 1){//支付宝支付
            //1、获取request里所有与alipay相关的参数，封装成一个map
            Map<String,String> param = Maps.newHashMap();
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
            PrintWriter out = null;
            try {
                out = response.getWriter();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(isPassed){
                Long billId = Long.parseLong(param.get("out_trade_no"));
                BusiBill busiBill = busiBillService.selectBusiBillById(billId);
                busiBill.setBillSign(1);

                int count= busiBillService.updateBusiBill(busiBill);
                SysUser sysUser = iSysUserService.selectUserById(busiBill.getUserId());
                sysUser.setDeposit(billId);
                iSysUserService.updateUser(sysUser);

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
        }else if(payType == 2){//微信支付

        }
    }

    /**
     * 退押金
     * @return
     */
    @Log(title = "退押金", businessType = BusinessType.INSERT)
    @PostMapping( "/returnDeposit")
    @ResponseBody
    public AjaxResult reDeposit(BusiBill busiBill){
        SysUser sysUser = iSysUserService.selectUserById(ShiroUtils.getUserId());
        if(sysUser.getDeposit()==0){
            return AjaxResult.error("未缴纳押金，无需退款");
        }
        if(busiBill.getUserId()==null){
            busiBill.setUserId(ShiroUtils.getUserId());
        }
        if(busiBill.getId()==null){
            busiBill.setId(sysUser.getDeposit());
        }
        BusiBookPreborrow preborrow=new BusiBookPreborrow();
        preborrow.setUserId(sysUser.getUserId());
        preborrow.setState(BusiBookPreborrow.StateType.UNFINISH.value());
        if(busiBookPreborrowService.selectBusiBookPreborrowList(preborrow).size()>0){
            return AjaxResult.error("请先将预约的图书取消！！！");
        }

        BusiBookBorrow busiBookBorrow=new BusiBookBorrow();
        busiBookBorrow.setUserId(sysUser.getUserId());
        busiBookBorrow.setState(BusiBookBorrow.StateType.UNRETURN.value());
        if(busiBookBorrowService.selectBusiBookBorrowList(busiBookBorrow).size()>0){
            return AjaxResult.error("请先归还借阅的图书！！！");
        }
        AjaxResult ajaxResult = tradeService.test_trade_refund(busiBillService.selectBusiBillById(busiBill.getId()));

        /*if(ajaxResult.get(AjaxResult.CODE_TAG).equals(AjaxResult.Type.SUCCESS)){
            busiBill.setMoney(busiBill.getMoney().negate());
            busiBillService.updateBusiBill(busiBill);
            SysUser sysUser = ShiroUtils.getSysUser();
            sysUser.setDeposit(0L);
            iSysUserService.updateUser(sysUser);
        }*/
        busiBill.setMoney(new BigDecimal(-100));
        busiBillService.updateBusiBill(busiBill);
        sysUser.setDeposit(0L);
        iSysUserService.updateUser(sysUser);
        return AjaxResult.success("支付宝退款成功");
    }

    @Log(title = "检测是否支付成功", businessType = BusinessType.INSERT)
    @PostMapping( "/checkPayDepositResult")
    @ResponseBody
    public AjaxResult checkPayDepositResult(){
        if(iSysUserService.alreadyPayDeposit(ShiroUtils.getUserId())){
            return AjaxResult.success("支付成功");
        }else {
            return AjaxResult.error("尚未支付成功");
        }
    }
}
