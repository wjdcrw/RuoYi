package com.ruoyi.busi.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 图书类型对象 busi_book_baseinfo
 * 
 * @author ruoyi
 * @date 2021-03-19
 */
public class BusiBookBaseinfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 图书编号 */
    private Long id;

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
            .toString();
    }
}
