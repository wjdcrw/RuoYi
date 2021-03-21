package com.ruoyi.busi.tools;

import java.util.Random;

/**
 * 验证码
 *
 * @author duanc
 * @version 1.0
 * @date 2021/3/21 23:19
 */
public class VerificationCodeTool {
    public static String getCode(){
        Random random = new Random();
        StringBuilder builder=new StringBuilder();
        for(int i=0;i<6;i++){
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(getCode());
    }
}
