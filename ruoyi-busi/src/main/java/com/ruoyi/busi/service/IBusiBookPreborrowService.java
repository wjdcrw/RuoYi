package com.ruoyi.busi.service;

import java.util.List;
import com.ruoyi.busi.domain.BusiBookPreborrow;
import com.ruoyi.common.core.domain.AjaxResult;

/**
 * 预约查询Service接口
 * 
 * @author ruoyi
 * @date 2021-03-28
 */
public interface IBusiBookPreborrowService 
{
    /**
     * 查询预约查询
     * 
     * @param id 预约查询ID
     * @return 预约查询
     */
    public BusiBookPreborrow selectBusiBookPreborrowById(Long id);

    /**
     * 查询预约查询列表
     * 
     * @param busiBookPreborrow 预约查询
     * @return 预约查询集合
     */
    public List<BusiBookPreborrow> selectBusiBookPreborrowList(BusiBookPreborrow busiBookPreborrow);

    /**
     * 新增预约查询
     * 
     * @param busiBookPreborrow 预约查询
     * @return 结果
     */
    public AjaxResult insertBusiBookPreborrow(BusiBookPreborrow busiBookPreborrow);

    /**
     * 修改预约查询
     * 
     * @param busiBookPreborrow 预约查询
     * @return 结果
     */
    public int updateBusiBookPreborrow(BusiBookPreborrow busiBookPreborrow);

    /**
     * 批量删除预约查询
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiBookPreborrowByIds(String ids);

    /**
     * 取消预约
     * @param busiBookPreborrow
     * @return
     */
    public int cancle(BusiBookPreborrow busiBookPreborrow);

    /**
     * 删除预约查询信息
     * 
     * @param id 预约查询ID
     * @return 结果
     */
    public int deleteBusiBookPreborrowById(Long id);

    /**
     * 处理超时的预约
     */
    public void preBorrowOverTimer();
}
