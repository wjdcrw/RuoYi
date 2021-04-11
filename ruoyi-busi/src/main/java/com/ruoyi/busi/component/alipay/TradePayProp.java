package com.ruoyi.busi.component.alipay;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author ：dcr
 * @description: 支付宝二维码存储与访问路径设置
 **/
@Component
@ConfigurationProperties(prefix = "trade.zhifu.qrcode")
public class TradePayProp {
    /**
     * 支付宝qrcode存储路径
     */
    private String aliPayPath;
    /**
     * 微信支付qrcode存储路径
     */
    private String weChatPath;
    /**
     * qrcode存储文件路径设置
     */
    private String storePath;
    /**
     * http访问路径设置
     */
    private String httpBasePath;
    /**
     * 支付成功回调页面
     */
    private String paySuccessCallBack;

    public String getAliPayPath() {
        return aliPayPath;
    }

    public TradePayProp setAliPayPath(String aliPayPath) {
        this.aliPayPath = aliPayPath;
        return this;
    }

    public String getWeChatPath() {
        return weChatPath;
    }

    public TradePayProp setWeChatPath(String weChatPath) {
        this.weChatPath = weChatPath;
        return this;
    }

    public String getStorePath() {
        return storePath;
    }

    public TradePayProp setStorePath(String storePath) {
        this.storePath = storePath;
        return this;
    }

    public String getHttpBasePath() {
        return httpBasePath;
    }

    public TradePayProp setHttpBasePath(String httpBasePath) {
        this.httpBasePath = httpBasePath;
        return this;
    }

    public String getPaySuccessCallBack() {
        return paySuccessCallBack;
    }

    public TradePayProp setPaySuccessCallBack(String paySuccessCallBack) {
        this.paySuccessCallBack = paySuccessCallBack;
        return this;
    }
}
