package com.ruoyi.busi.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 借阅查询对象 busi_book_borrow
 * 
 * @author ruoyi
 * @date 2021-03-29
 */
public class BusiBookBorrow extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    public static final int MAXPERIOD=35;
    public static final int MAXBORROWCOUNT=3;
    public static final int ONCEEXTEND=7;

    public  enum StateType{
        /** 未还 */
        UNRETURN(0),
        /** 已还 */
        RETURN(1);
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

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    private String userLoginName;

    private String userName;

    /** 图书id */
    @Excel(name = "图书id")
    private Long bookId;

    private String bookName;

    /** 借出时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "借出时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date borrowDate;

    /** 借阅时间 */
    @Excel(name = "借阅时间")
    private Integer borrowPeriod;

    /** 还书时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "还书时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date returnDate;

    /** 是否还书：0-未还 1-已还 */
    @Excel(name = "是否还书：0-未还 1-已还")
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
    public void setBorrowDate(Date borrowDate) 
    {
        this.borrowDate = borrowDate;
    }

    public Date getBorrowDate() 
    {
        return borrowDate;
    }
    public void setBorrowPeriod(Integer borrowPeriod) 
    {
        this.borrowPeriod = borrowPeriod;
    }

    public Integer getBorrowPeriod() 
    {
        return borrowPeriod;
    }
    public void setReturnDate(Date returnDate) 
    {
        this.returnDate = returnDate;
    }

    public Date getReturnDate() 
    {
        return returnDate;
    }
    public void setState(Integer state) 
    {
        this.state = state;
    }

    public Integer getState() 
    {
        return state;
    }

    public String getUserLoginName() {
        return userLoginName;
    }

    public BusiBookBorrow setUserLoginName(String userLoginName) {
        this.userLoginName = userLoginName;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public BusiBookBorrow setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getBookName() {
        return bookName;
    }

    public BusiBookBorrow setBookName(String bookName) {
        this.bookName = bookName;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("bookId", getBookId())
            .append("borrowDate", getBorrowDate())
            .append("borrowPeriod", getBorrowPeriod())
            .append("returnDate", getReturnDate())
            .append("state", getState())
            .append("remark", getRemark())
            .toString();
    }
}
