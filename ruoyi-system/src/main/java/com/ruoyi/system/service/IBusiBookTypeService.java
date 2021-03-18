package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.BusiBookType;

/**
 * 图书类型Service接口
 * 
 * @author ruoyi
 * @date 2021-03-18
 */
public interface IBusiBookTypeService 
{
    /**
     * 查询图书类型
     * 
     * @param id 图书类型ID
     * @return 图书类型
     */
    public BusiBookType selectBusiBookTypeById(Long id);

    /**
     * 查询图书类型列表
     * 
     * @param busiBookType 图书类型
     * @return 图书类型集合
     */
    public List<BusiBookType> selectBusiBookTypeList(BusiBookType busiBookType);

    /**
     * 新增图书类型
     * 
     * @param busiBookType 图书类型
     * @return 结果
     */
    public int insertBusiBookType(BusiBookType busiBookType);

    /**
     * 修改图书类型
     * 
     * @param busiBookType 图书类型
     * @return 结果
     */
    public int updateBusiBookType(BusiBookType busiBookType);

    /**
     * 批量删除图书类型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiBookTypeByIds(String ids);

    /**
     * 删除图书类型信息
     * 
     * @param id 图书类型ID
     * @return 结果
     */
    public int deleteBusiBookTypeById(Long id);
}
