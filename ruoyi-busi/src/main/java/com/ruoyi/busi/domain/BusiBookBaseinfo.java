package com.ruoyi.busi.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 图书管理对象 busi_book_baseinfo
 * 
 * @author ruoyi
 * @date 2021-03-22
 */
public class BusiBookBaseinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public  enum StateType{
        /** 空闲 */
        FREE(0),
        /** 预约 */
        APPOINTMENT(1),
        /** 借阅 */
        BORROW(2),
        /** 报废 */
        SCRAP(3);
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

    /** 图书编号 */
    private Long id;

    /** 部门编号 */
    @Excel(name = "部门编号")
    private Long deptId;

    private String deptName;
    /** 图书名称 */
    @Excel(name = "图书名称")
    private String name;

    /** 作者 */
    @Excel(name = "作者")
    private String author;

    /** 图书类别编号 */
    @Excel(name = "图书类别编号")
    private Integer bookType;

    /** ISBN编号 */
    @Excel(name = "ISBN编号")
    private String ISBN;

    /** 出版社 */
    @Excel(name = "出版社")
    private String publisher;

    /** 出版日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出版日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishTime;

    /** 价格 */
    private BigDecimal price;

    /** 状态：0-闲置 1-预借 2-借出 */
    @Excel(name = "状态：0-闲置 1-预借 2-借出")
    private Integer state;

    /** 简介 */
    private String introduce;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setAuthor(String author) 
    {
        this.author = author;
    }

    public String getAuthor() 
    {
        return author;
    }
    public void setBookType(Integer bookType) 
    {
        this.bookType = bookType;
    }

    public Integer getBookType() 
    {
        return bookType;
    }
    public void setISBN(String ISBN) 
    {
        this.ISBN = ISBN;
    }

    public String getISBN() 
    {
        return ISBN;
    }
    public void setPublisher(String publisher) 
    {
        this.publisher = publisher;
    }

    public String getPublisher() 
    {
        return publisher;
    }
    public void setPublishTime(Date publishTime) 
    {
        this.publishTime = publishTime;
    }

    public Date getPublishTime() 
    {
        return publishTime;
    }
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public BigDecimal getPrice()
    {
        return price;
    }
    public void setState(Integer state)
    {
        this.state = state;
    }

    public Integer getState() 
    {
        return state;
    }
    public void setIntroduce(String introduce) 
    {
        this.introduce = introduce;
    }

    public String getIntroduce() 
    {
        return introduce;
    }

    public Long getDeptId() {
        return deptId;
    }

    public BusiBookBaseinfo setDeptId(Long deptId) {
        this.deptId = deptId;
        return this;
    }

    public String getDeptName() {
        return deptName;
    }

    public BusiBookBaseinfo setDeptName(String deptName) {
        this.deptName = deptName;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("author", getAuthor())
            .append("bookType", getBookType())
            .append("ISBN", getISBN())
            .append("publisher", getPublisher())
            .append("publishTime", getPublishTime())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("price", getPrice())
            .append("state", getState())
            .append("introduce", getIntroduce())
            .toString();
    }
}
