package com.ruoyi.busi.service;


import com.ruoyi.busi.domain.BusiBill;
import com.ruoyi.common.core.domain.AjaxResult;

/**
 * @author ：dcr
 * @description: 交易服务
 **/
public interface TradeService {

    /**
     * 根据订单生成支付二维码
     * @param
     * @param
     * @return
     */
    AjaxResult tradeQrCode(BusiBill busiBill);

    /**
     * 查询订单支付状态
     * @param orderId
     * @return
     */
    AjaxResult tradeStatusQuery(Long orderId, Integer payType);

    public AjaxResult test_trade_refund(BusiBill busiBill) ;
}
