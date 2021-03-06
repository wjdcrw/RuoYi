package com.ruoyi.busi.service;

import com.ruoyi.busi.domain.BusiBookBaseinfo;

import java.util.List;

/**
 * 图书类型Service接口
 * 
 * @author ruoyi
 * @date 2021-03-19
 */
public interface IBusiBookBaseinfoService 
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

    /**
     * 批量删除图书类型
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiBookBaseinfoByIds(String ids);

    /**
     * 删除图书类型信息
     * 
     * @param id 图书类型ID
     * @return 结果
     */
    public int deleteBusiBookBaseinfoById(Long id);


    /**
     * 批量修改图书状态
     * @param ids
     * @param state
     * @return
     */
    public int updateBusiBookBaseinfoState(List<Long>ids,int state);
}
