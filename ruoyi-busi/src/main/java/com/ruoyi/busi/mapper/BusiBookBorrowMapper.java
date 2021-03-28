package com.ruoyi.busi.mapper;

import java.util.List;
import com.ruoyi.busi.domain.BusiBookBorrow;

/**
 * 借阅查询Mapper接口
 * 
 * @author ruoyi
 * @date 2021-03-29
 */
public interface BusiBookBorrowMapper 
{
    /**
     * 查询借阅查询
     * 
     * @param id 借阅查询ID
     * @return 借阅查询
     */
    public BusiBookBorrow selectBusiBookBorrowById(Long id);

    /**
     * 查询借阅查询列表
     * 
     * @param busiBookBorrow 借阅查询
     * @return 借阅查询集合
     */
    public List<BusiBookBorrow> selectBusiBookBorrowList(BusiBookBorrow busiBookBorrow);

    /**
     * 新增借阅查询
     * 
     * @param busiBookBorrow 借阅查询
     * @return 结果
     */
    public int insertBusiBookBorrow(BusiBookBorrow busiBookBorrow);

    /**
     * 修改借阅查询
     * 
     * @param busiBookBorrow 借阅查询
     * @return 结果
     */
    public int updateBusiBookBorrow(BusiBookBorrow busiBookBorrow);

    /**
     * 删除借阅查询
     * 
     * @param id 借阅查询ID
     * @return 结果
     */
    public int deleteBusiBookBorrowById(Long id);

    /**
     * 批量删除借阅查询
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiBookBorrowByIds(String[] ids);
}
