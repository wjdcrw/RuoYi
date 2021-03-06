package com.ruoyi.busi.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 预约查询对象 busi_book_preBorrow
 * 
 * @author ruoyi
 * @date 2021-03-28
 */
public class BusiBookPreborrow extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public  enum StateType{
        /** 预约中 */
        UNFINISH(0),
        /** 完成 */
        FINISH(1),
        /** 超时 */
        OVERTIME(2),
        /** 取消 */
        CANCLE(3);
        private final int value;

        StateType(int value)
        {
            this.value = value;
        }

        public int value()
        {
            return this.value;
        }
    }
    /**  */
    private Long id;

    /** 图书id */
    @Excel(name = "用户编号")
    private Long userId;

    private String userName;

    private String userLoginName;
    /** 用户id */
    @Excel(name = "图书编号")
    private Long bookId;

    private String bookName;

    /** 完成时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "完成时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date finishTime;

    /** 状态 0：未完成 1：已完成 2：超时 */
    @Excel(name = "状态 0：未完成 1：已完成 2：超时")
    private Integer state;

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
    public void setFinishTime(Date finishTime) 
    {
        this.finishTime = finishTime;
    }

    public Date getFinishTime() 
    {
        return finishTime;
    }
    public void setState(Integer state) 
    {
        this.state = state;
    }

    public Integer getState() 
    {
        return state;
    }

    public String getUserName() {
        return userName;
    }

    public BusiBookPreborrow setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public BusiBookPreborrow setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
        return this;
    }

    public String getBookName() {
        return bookName;
    }

    public BusiBookPreborrow setBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("bookId", getBookId())
            .append("createTime", getCreateTime())
            .append("finishTime", getFinishTime())
            .append("state", getState())
            .toString();
    }
}
