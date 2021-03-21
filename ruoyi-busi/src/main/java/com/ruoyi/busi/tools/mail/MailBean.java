package com.ruoyi.busi.tools.mail;

import com.ruoyi.busi.tools.remind.RemindTool;

/**
 * 邮件信息
 *
 * @author duanc
 * @version 1.0
 * @date 2021/3/21 23:13
 */
public class MailBean {
    //接收地址
    private String recipientAddress;
    //主题
    private String subject;
    //正文
    private String context;

    /**
     * 预借验证码邮件模板
     * @param recipientAddress
     * @param bookName
     * @param code
     * @return
     */
    public static MailBean getPreBorrowMailTemplate(String recipientAddress,String bookName,String code){
        String preBorrowSubject="预借码";
        MailBean mailBean=new MailBean().setRecipientAddress(recipientAddress)
                .setSubject(preBorrowSubject)
                .setContext(RemindTool.getPreBorrowText(bookName,code));
        return mailBean;
    }

    /**
     * 借阅验证码邮件模板
     * @param recipientAddress
     * @param bookName
     * @param code
     * @return
     */
    public static MailBean getBorrowMailTemplate(String recipientAddress,String bookName,String code){
        String context="您好，您借取的["+bookName+"]取书码为["+code+"],有效期5分钟";
        String preBorrowSubject="预借码";
        MailBean mailBean=new MailBean().setRecipientAddress(recipientAddress)
                .setSubject(preBorrowSubject)
                .setContext(RemindTool.getBorrowText(bookName,code));
        return mailBean;
    }
    public String getRecipientAddress() {
        return recipientAddress;
    }

    public MailBean setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress;
        return this;
    }

    public String getSubject() {
        return subject;
    }

    public MailBean setSubject(String subject) {
        this.subject = subject;
        return this;
    }

    public String getContext() {
        return context;
    }

    public MailBean setContext(String context) {
        this.context = context;
        return this;
    }
}
