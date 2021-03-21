package com.ruoyi.busi.tools.remind;

/**
 * 站内提醒
 *
 * @author duanc
 * @version 1.0
 * @date 2021/3/21 23:39
 */
public class RemindTool {
    public static String getPreBorrowText(String bookName,String code){
        String text="您好，已成功预借["+bookName+"]，取书码为["+code+"],有效期24小时。/n若不能取书，请及时取消预借。";
        return text;
    }

    public static String getBorrowText(String bookName,String code){
        String text= "您好，您借取的["+bookName+"]取书码为["+code+"],有效期5分钟";
        return text;
    }

    public static boolean sendText(String userId,String text){
        return true;
    }
}
