package com.ruoyi.busi.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 账单管理对象 busi_bill
 * 
 * @author ruoyi
 * @date 2021-04-11
 */
public class BusiBill extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 用户登录名 */
    private String userLoginName;

    /** 用户昵称 */
    private String userName;


    /** 图书编码 */
    @Excel(name = "图书编码")
    private Long bookId;

    /** 图书名称 */
    private String bookName;

    /** 金额 */
    @Excel(name = "金额")
    private BigDecimal money;

    /** 账单类型：0押金、1损坏赔偿、2丢失赔偿、3超时赔偿 */
    @Excel(name = "账单类型：0押金、1损坏赔偿、2丢失赔偿、3超时赔偿")
    private Integer billType;

    /** 账单标识：0-未支付 1-现金 2-支付宝 */
    @Excel(name = "账单标识：0-未支付 1-现金 2-支付宝")
    private Integer billSign;

    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setBookId(Long bookId) 
    {
        this.bookId = bookId;
    }

    public Long getBookId() 
    {
        return bookId;
    }
    public void setMoney(BigDecimal money)
    {
        this.money = money;
    }

    public BigDecimal getMoney()
    {
        return money;
    }
    public void setBillType(Integer billType) 
    {
        this.billType = billType;
    }

    public Integer getBillType() 
    {
        return billType;
    }
    public void setBillSign(Integer billSign) 
    {
        this.billSign = billSign;
    }

    public Integer getBillSign() 
    {
        return billSign;
    }
    public void setPayTime(Date payTime) 
    {
        this.payTime = payTime;
    }

    public Date getPayTime() 
    {
        return payTime;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public BusiBill setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public BusiBill setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getBookName() {
        return bookName;
    }

    public BusiBill setBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("userLoginName",getUserLoginName())
            .append("bookId", getBookId())
            .append("money", getMoney())
            .append("billType", getBillType())
            .append("billSign", getBillSign())
            .append("createTime", getCreateTime())
            .append("payTime", getPayTime())
            .append("remark", getRemark())
            .toString();
    }
}
