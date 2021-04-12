package com.ruoyi.busi.config;

import com.ruoyi.busi.component.alipay.TradePayProp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * TODO
 *
 * @author duanc
 * @version 1.0
 * @date 2021/4/12 21:53
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private TradePayProp tradePayProp;
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name");
        if(os.toLowerCase().startsWith("win")){ //windows系统
            /** QrCode图片存储路径 */
            registry.addResourceHandler(tradePayProp.getHttpBasePath()
                    +"/**")
                    .addResourceLocations("file:" + tradePayProp.getStorePath() + "/");
        }else{ //linux或者mac

        }
    }
}
