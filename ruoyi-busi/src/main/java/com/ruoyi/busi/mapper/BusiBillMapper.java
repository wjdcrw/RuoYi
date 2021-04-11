package com.ruoyi.busi.mapper;

import java.util.List;
import com.ruoyi.busi.domain.BusiBill;

/**
 * 账单管理Mapper接口
 * 
 * @author ruoyi
 * @date 2021-03-29
 */
public interface BusiBillMapper 
{
    /**
     * 查询账单管理
     * 
     * @param id 账单管理ID
     * @return 账单管理
     */
    public BusiBill selectBusiBillById(Long id);

    /**
     * 查询账单管理列表
     * 
     * @param busiBill 账单管理
     * @return 账单管理集合
     */
    public List<BusiBill> selectBusiBillList(BusiBill busiBill);

    /**
     * 新增账单管理
     * 
     * @param busiBill 账单管理
     * @return 结果
     */
    public Long insertBusiBill(BusiBill busiBill);

    /**
     * 修改账单管理
     * 
     * @param busiBill 账单管理
     * @return 结果
     */
    public int updateBusiBill(BusiBill busiBill);

    /**
     * 删除账单管理
     * 
     * @param id 账单管理ID
     * @return 结果
     */
    public int deleteBusiBillById(Long id);

    /**
     * 批量删除账单管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiBillByIds(String[] ids);
}
