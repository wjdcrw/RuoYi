package com.ruoyi.busi.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.busi.mapper.BusiBillMapper;
import com.ruoyi.busi.domain.BusiBill;
import com.ruoyi.busi.service.IBusiBillService;
import com.ruoyi.common.core.text.Convert;

/**
 * 账单管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-03-29
 */
@Service
public class BusiBillServiceImpl implements IBusiBillService 
{
    @Autowired
    private BusiBillMapper busiBillMapper;

    /**
     * 查询账单管理
     * 
     * @param id 账单管理ID
     * @return 账单管理
     */
    @Override
    public BusiBill selectBusiBillById(Long id)
    {
        return busiBillMapper.selectBusiBillById(id);
    }

    /**
     * 查询账单管理列表
     * 
     * @param busiBill 账单管理
     * @return 账单管理
     */
    @Override
    public List<BusiBill> selectBusiBillList(BusiBill busiBill)
    {
        return busiBillMapper.selectBusiBillList(busiBill);
    }

    /**
     * 新增账单管理
     * 
     * @param busiBill 账单管理
     * @return 结果
     */
    @Override
    public int insertBusiBill(BusiBill busiBill)
    {
        busiBill.setCreateTime(DateUtils.getNowDate());
        return busiBillMapper.insertBusiBill(busiBill);
    }

    /**
     * 修改账单管理
     * 
     * @param busiBill 账单管理
     * @return 结果
     */
    @Override
    public int updateBusiBill(BusiBill busiBill)
    {
        return busiBillMapper.updateBusiBill(busiBill);
    }

    /**
     * 删除账单管理对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBusiBillByIds(String ids)
    {
        return busiBillMapper.deleteBusiBillByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除账单管理信息
     * 
     * @param id 账单管理ID
     * @return 结果
     */
    @Override
    public int deleteBusiBillById(Long id)
    {
        return busiBillMapper.deleteBusiBillById(id);
    }
}
