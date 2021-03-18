package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.BusiBookTypeMapper;
import com.ruoyi.system.domain.BusiBookType;
import com.ruoyi.system.service.IBusiBookTypeService;
import com.ruoyi.common.core.text.Convert;

/**
 * 图书类型Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-03-18
 */
@Service
public class BusiBookTypeServiceImpl implements IBusiBookTypeService 
{
    @Autowired
    private BusiBookTypeMapper busiBookTypeMapper;

    /**
     * 查询图书类型
     * 
     * @param id 图书类型ID
     * @return 图书类型
     */
    @Override
    public BusiBookType selectBusiBookTypeById(Long id)
    {
        return busiBookTypeMapper.selectBusiBookTypeById(id);
    }

    /**
     * 查询图书类型列表
     * 
     * @param busiBookType 图书类型
     * @return 图书类型
     */
    @Override
    public List<BusiBookType> selectBusiBookTypeList(BusiBookType busiBookType)
    {
        return busiBookTypeMapper.selectBusiBookTypeList(busiBookType);
    }

    /**
     * 新增图书类型
     * 
     * @param busiBookType 图书类型
     * @return 结果
     */
    @Override
    public int insertBusiBookType(BusiBookType busiBookType)
    {
        return busiBookTypeMapper.insertBusiBookType(busiBookType);
    }

    /**
     * 修改图书类型
     * 
     * @param busiBookType 图书类型
     * @return 结果
     */
    @Override
    public int updateBusiBookType(BusiBookType busiBookType)
    {
        return busiBookTypeMapper.updateBusiBookType(busiBookType);
    }

    /**
     * 删除图书类型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBusiBookTypeByIds(String ids)
    {
        return busiBookTypeMapper.deleteBusiBookTypeByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除图书类型信息
     * 
     * @param id 图书类型ID
     * @return 结果
     */
    @Override
    public int deleteBusiBookTypeById(Long id)
    {
        return busiBookTypeMapper.deleteBusiBookTypeById(id);
    }
}
