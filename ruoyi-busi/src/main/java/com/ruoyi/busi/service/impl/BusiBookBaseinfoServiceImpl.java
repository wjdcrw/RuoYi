package com.ruoyi.busi.service.impl;

import com.ruoyi.busi.domain.BusiBookBaseinfo;
import com.ruoyi.busi.domain.Result;
import com.ruoyi.busi.mapper.BusiBookBaseinfoMapper;
import com.ruoyi.busi.mapper.BusiBookPreborrowMapper;
import com.ruoyi.busi.service.IBusiBookBaseinfoService;
import com.ruoyi.common.core.text.Convert;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 图书类型Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-03-19
 */
@Service
public class BusiBookBaseinfoServiceImpl implements IBusiBookBaseinfoService 
{
    @Autowired
    private BusiBookBaseinfoMapper busiBookBaseinfoMapper;
    @Autowired
    private BusiBookPreborrowMapper busiBookPreborrowMapper;

    /**
     * 查询图书类型
     * 
     * @param id 图书类型ID
     * @return 图书类型
     */
    @Override
    public BusiBookBaseinfo selectBusiBookBaseinfoById(Long id)
    {
        return busiBookBaseinfoMapper.selectBusiBookBaseinfoById(id);
    }

    /**
     * 查询图书类型列表
     * 
     * @param busiBookBaseinfo 图书类型
     * @return 图书类型
     */
    @Override
    public List<BusiBookBaseinfo> selectBusiBookBaseinfoList(BusiBookBaseinfo busiBookBaseinfo)
    {
        return busiBookBaseinfoMapper.selectBusiBookBaseinfoList(busiBookBaseinfo);
    }

    /**
     * 新增图书类型
     * 
     * @param busiBookBaseinfo 图书类型
     * @return 结果
     */
    @Override
    public int insertBusiBookBaseinfo(BusiBookBaseinfo busiBookBaseinfo)
    {
        busiBookBaseinfo.setCreateTime(DateUtils.getNowDate());
        return busiBookBaseinfoMapper.insertBusiBookBaseinfo(busiBookBaseinfo);
    }

    /**
     * 修改图书类型
     * 
     * @param busiBookBaseinfo 图书类型
     * @return 结果
     */
    @Override
    public int updateBusiBookBaseinfo(BusiBookBaseinfo busiBookBaseinfo)
    {
        busiBookBaseinfo.setUpdateTime(DateUtils.getNowDate());
        return busiBookBaseinfoMapper.updateBusiBookBaseinfo(busiBookBaseinfo);
    }

    /**
     * 删除图书类型对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBusiBookBaseinfoByIds(String ids)
    {
        return busiBookBaseinfoMapper.deleteBusiBookBaseinfoByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除图书类型信息
     * 
     * @param id 图书类型ID
     * @return 结果
     */
    @Override
    public int deleteBusiBookBaseinfoById(Long id)
    {
        return busiBookBaseinfoMapper.deleteBusiBookBaseinfoById(id);
    }


    /*public Result deleteBusiBookBaseinfoById(Long id){
        Result result=new Result();
        BusiBookBaseinfo busiBookBaseinfo = busiBookBaseinfoMapper.selectBusiBookBaseinfoById(id);
        if(busiBookBaseinfo.getState()!=0){
            result.setState(Result.FAIL);
            result.setMessage("此图书处于预借或借阅状态，不可删除！！！");
            return result;
        }
        busiBookBaseinfoMapper.deleteBusiBookBaseinfoById(id);
        result.setState(Result.SUCCESS);
        return result;
    }*/
}
