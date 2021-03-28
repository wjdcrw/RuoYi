package com.ruoyi.busi.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.busi.mapper.BusiBookBorrowMapper;
import com.ruoyi.busi.domain.BusiBookBorrow;
import com.ruoyi.busi.service.IBusiBookBorrowService;
import com.ruoyi.common.core.text.Convert;

/**
 * 借阅查询Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-03-29
 */
@Service
public class BusiBookBorrowServiceImpl implements IBusiBookBorrowService 
{
    @Autowired
    private BusiBookBorrowMapper busiBookBorrowMapper;

    /**
     * 查询借阅查询
     * 
     * @param id 借阅查询ID
     * @return 借阅查询
     */
    @Override
    public BusiBookBorrow selectBusiBookBorrowById(Long id)
    {
        return busiBookBorrowMapper.selectBusiBookBorrowById(id);
    }

    /**
     * 查询借阅查询列表
     * 
     * @param busiBookBorrow 借阅查询
     * @return 借阅查询
     */
    @Override
    public List<BusiBookBorrow> selectBusiBookBorrowList(BusiBookBorrow busiBookBorrow)
    {
        return busiBookBorrowMapper.selectBusiBookBorrowList(busiBookBorrow);
    }

    /**
     * 新增借阅查询
     * 
     * @param busiBookBorrow 借阅查询
     * @return 结果
     */
    @Override
    public int insertBusiBookBorrow(BusiBookBorrow busiBookBorrow)
    {
        return busiBookBorrowMapper.insertBusiBookBorrow(busiBookBorrow);
    }

    /**
     * 修改借阅查询
     * 
     * @param busiBookBorrow 借阅查询
     * @return 结果
     */
    @Override
    public int updateBusiBookBorrow(BusiBookBorrow busiBookBorrow)
    {
        return busiBookBorrowMapper.updateBusiBookBorrow(busiBookBorrow);
    }

    /**
     * 删除借阅查询对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBusiBookBorrowByIds(String ids)
    {
        return busiBookBorrowMapper.deleteBusiBookBorrowByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除借阅查询信息
     * 
     * @param id 借阅查询ID
     * @return 结果
     */
    @Override
    public int deleteBusiBookBorrowById(Long id)
    {
        return busiBookBorrowMapper.deleteBusiBookBorrowById(id);
    }
}
