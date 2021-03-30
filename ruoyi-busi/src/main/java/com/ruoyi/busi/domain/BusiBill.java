package com.ruoyi.busi.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 账单管理对象 busi_bill
 * 
 * @author ruoyi
 * @date 2021-03-29
 */
public class BusiBill extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /**  */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 图书编码 */
    @Excel(name = "图书编码")
    private Long bookId;

    /** 金额 */
    @Excel(name = "金额")
    private Long money;

    /** 上一个节点(id) */
    @Excel(name = "上一个节点(id)")
    private Long preId;

    /** 账单类型：0押金、1损坏赔偿、2丢失赔偿、3超时赔偿 */
    @Excel(name = "账单类型：0押金、1损坏赔偿、2丢失赔偿、3超时赔偿")
    private Integer billType;

    /** 账单标识：0-入账 1出账 */
    @Excel(name = "账单标识：0-入账 1出账")
    private Integer billSign;

    /** 父id */
    private Long parentId;

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
    public void setMoney(Long money) 
    {
        this.money = money;
    }

    public Long getMoney() 
    {
        return money;
    }
    public void setPreId(Long preId) 
    {
        this.preId = preId;
    }

    public Long getPreId() 
    {
        return preId;
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
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("bookId", getBookId())
            .append("money", getMoney())
            .append("preId", getPreId())
            .append("billType", getBillType())
            .append("billSign", getBillSign())
            .append("createTime", getCreateTime())
            .append("remark", getRemark())
            .append("parentId", getParentId())
            .toString();
    }
}
