package com.ruoyi.busi.service.impl;

import java.util.List;

import com.ruoyi.busi.domain.BusiBookBaseinfo;
import com.ruoyi.busi.mapper.BusiBookBaseinfoMapper;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.busi.mapper.BusiBookPreborrowMapper;
import com.ruoyi.busi.domain.BusiBookPreborrow;
import com.ruoyi.busi.service.IBusiBookPreborrowService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

/**
 * 预约查询Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-03-28
 */
@Service
@Transactional
public class BusiBookPreborrowServiceImpl implements IBusiBookPreborrowService 
{
    @Autowired
    private BusiBookPreborrowMapper busiBookPreborrowMapper;
    @Autowired
    private BusiBookBaseinfoMapper busiBookBaseinfoMapper;

    /**
     * 查询预约查询
     * 
     * @param id 预约查询ID
     * @return 预约查询
     */
    @Override
    public BusiBookPreborrow selectBusiBookPreborrowById(Long id)
    {
        return busiBookPreborrowMapper.selectBusiBookPreborrowById(id);
    }

    /**
     * 查询预约查询列表
     * 
     * @param busiBookPreborrow 预约查询
     * @return 预约查询
     */
    @Override
    public List<BusiBookPreborrow> selectBusiBookPreborrowList(BusiBookPreborrow busiBookPreborrow)
    {
        return busiBookPreborrowMapper.selectBusiBookPreborrowList(busiBookPreborrow);
    }

    /**
     * 新增预约查询
     * 
     * @param busiBookPreborrow 预约查询
     * @return 结果
     */
    @Override
    public int insertBusiBookPreborrow(BusiBookPreborrow busiBookPreborrow)
    {
        BusiBookBaseinfo busiBookBaseinfo = busiBookBaseinfoMapper.selectBusiBookBaseinfoById(busiBookPreborrow.getBookId());
        if(busiBookBaseinfo.getState()!=0){
            return 0;
        }
        busiBookBaseinfo.setState(1);
        busiBookBaseinfoMapper.updateBusiBookBaseinfo(busiBookBaseinfo);
        return busiBookPreborrowMapper.insertBusiBookPreborrow(busiBookPreborrow);
    }

    /**
     * 修改预约查询
     * 
     * @param busiBookPreborrow 预约查询
     * @return 结果
     */
    @Override
    public int updateBusiBookPreborrow(BusiBookPreborrow busiBookPreborrow)
    {
        return busiBookPreborrowMapper.updateBusiBookPreborrow(busiBookPreborrow);
    }

    /**
     * 删除预约查询对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBusiBookPreborrowByIds(String ids)
    {
        return busiBookPreborrowMapper.deleteBusiBookPreborrowByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除预约查询信息
     * 
     * @param id 预约查询ID
     * @return 结果
     */
    @Override
    public int deleteBusiBookPreborrowById(Long id)
    {
        return busiBookPreborrowMapper.deleteBusiBookPreborrowById(id);
    }
}
