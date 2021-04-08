package com.ruoyi.busi.mapper;

import java.util.List;
import com.ruoyi.busi.domain.BusiBookPreborrow;
import org.apache.ibatis.annotations.Param;

/**
 * 预约查询Mapper接口
 * 
 * @author ruoyi
 * @date 2021-03-28
 */
public interface BusiBookPreborrowMapper 
{
    /**
     * 查询预约查询
     * 
     * @param id 预约查询ID
     * @return 预约查询
     */
    public BusiBookPreborrow selectBusiBookPreborrowById(Long id);

    public BusiBookPreborrow selectCurrentBusiBookPreborrow(BusiBookPreborrow busiBookPreborrow);

    /**
     * 查询预约查询列表
     * 
     * @param busiBookPreborrow 预约查询
     * @return 预约查询集合
     */
    public List<BusiBookPreborrow> selectBusiBookPreborrowList(BusiBookPreborrow busiBookPreborrow);

    /**
     * 查询处于未完成状态但超时的预约数据
     * @return
     */
    public List<BusiBookPreborrow> selectBusiBookPreborrowOverTimerList();

    /**
     * 新增预约查询
     * 
     * @param busiBookPreborrow 预约查询
     * @return 结果
     */
    public int insertBusiBookPreborrow(BusiBookPreborrow busiBookPreborrow);

    /**
     * 修改预约查询
     * 
     * @param busiBookPreborrow 预约查询
     * @return 结果
     */
    public int updateBusiBookPreborrow(BusiBookPreborrow busiBookPreborrow);

    /**
     * 删除预约查询
     * 
     * @param id 预约查询ID
     * @return 结果
     */
    public int deleteBusiBookPreborrowById(Long id);

    /**
     * 批量删除预约查询
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiBookPreborrowByIds(String[] ids);

    /**
     * 批量修改预约状态
     * @param ids
     * @param state
     * @return
     */
    public int updateBusiBookPreborrowState(@Param("ids") List<Long> ids, @Param("state") int state);
}
