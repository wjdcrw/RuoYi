package com.ruoyi.busi.mapper;

import com.ruoyi.busi.domain.BusiBookBaseinfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 图书类型Mapper接口
 * 
 * @author ruoyi
 * @date 2021-03-19
 */
public interface BusiBookBaseinfoMapper 
{
    /**
     * 查询图书类型
     * 
     * @param id 图书类型ID
     * @return 图书类型
     */
    public BusiBookBaseinfo selectBusiBookBaseinfoById(Long id);

    /**
     * 查询图书类型列表
     * 
     * @param busiBookBaseinfo 图书类型
     * @return 图书类型集合
     */
    public List<BusiBookBaseinfo> selectBusiBookBaseinfoList(BusiBookBaseinfo busiBookBaseinfo);

    /**
     * 新增图书类型
     * 
     * @param busiBookBaseinfo 图书类型
     * @return 结果
     */
    public int insertBusiBookBaseinfo(BusiBookBaseinfo busiBookBaseinfo);

    /**
     * 修改图书类型
     * 
     * @param busiBookBaseinfo 图书类型
     * @return 结果
     */
    public int updateBusiBookBaseinfo(BusiBookBaseinfo busiBookBaseinfo);

    public int updateBusiBookBaseinfoState(@Param("ids")List<Long> ids, @Param("state")int state);

    /**
     * 删除图书类型
     * 
     * @param id 图书类型ID
     * @return 结果
     */
    public int deleteBusiBookBaseinfoById(Long id);

    /**
     * 批量删除图书类型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiBookBaseinfoByIds(String[] ids);
}
