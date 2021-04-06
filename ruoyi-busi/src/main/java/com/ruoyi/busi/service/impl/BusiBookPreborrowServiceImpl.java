package com.ruoyi.busi.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.busi.domain.BusiBookBaseinfo;
import com.ruoyi.busi.domain.Result;
import com.ruoyi.busi.mapper.BusiBookBaseinfoMapper;
import com.ruoyi.common.utils.DateUtils;
import org.apache.shiro.SecurityUtils;
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
        //若图书状态不为空闲，预约失败
        if(busiBookBaseinfo.getState()!=0){
            return 0;
        }
        //检查读者当前预约数
        if(currentPreBorrowNum(busiBookPreborrow.getUserId())>0){
            return 0;
        }
        //检查读者当前押金
        busiBookBaseinfo.setState(1);
        busiBookBaseinfoMapper.updateBusiBookBaseinfo(busiBookBaseinfo);
        return busiBookPreborrowMapper.insertBusiBookPreborrow(busiBookPreborrow);
    }

    /*public Result insertBusiBookPreborrow(BusiBookPreborrow busiBookPreborrow)
    {
        Result result=new Result();
        BusiBookBaseinfo busiBookBaseinfo = busiBookBaseinfoMapper.selectBusiBookBaseinfoById(busiBookPreborrow.getBookId());
        //若图书状态不为空闲，预约失败
        if(busiBookBaseinfo.getState()!=0){
            result.setState(Result.FAIL);
            result.setMessage("请选择图书状态为空闲的图书！！！");
            return result;
        }
        //检查读者当前预约数
        if(currentPreBorrowNum(busiBookPreborrow.getUserId())>0){
            result.setState(Result.FAIL);
            result.setMessage("您最多只可预约一本图书！！！");
            return result;
        }
        busiBookBaseinfo.setState(1);
        //更新图书状态
        busiBookBaseinfoMapper.updateBusiBookBaseinfo(busiBookBaseinfo);
        //添加预约书籍
        busiBookPreborrowMapper.insertBusiBookPreborrow(busiBookPreborrow);
        result.setState(Result.SUCCESS);
        return result;
    }*/
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

    @Override
    @Transactional
    public int cancle(BusiBookPreborrow busiBookPreborrow) {
        try{
            busiBookPreborrow = busiBookPreborrowMapper.selectBusiBookPreborrowById(busiBookPreborrow.getId());
            busiBookPreborrow.setState(3);
            busiBookPreborrow.setFinishTime(DateUtils.getNowDate());
            List<Long> bookIds=new ArrayList<>();
            bookIds.add(busiBookPreborrow.getBookId());
            busiBookBaseinfoMapper.updateBusiBookBaseinfoState(bookIds,0);
            busiBookPreborrowMapper.updateBusiBookPreborrow(busiBookPreborrow);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }

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

    @Override
    @Transactional
    public void preBorrowOverTimer() {
        List<BusiBookPreborrow> busiBookPreborrows = busiBookPreborrowMapper.selectBusiBookPreborrowOverTimerList();
        List<Long> bookids=new ArrayList<>();
        List<Long> preBorrowIds=new ArrayList<>();
        for(BusiBookPreborrow _busiBookPreborrow:busiBookPreborrows){
            bookids.add(_busiBookPreborrow.getBookId());
            preBorrowIds.add(_busiBookPreborrow.getId());
        }
        if(busiBookPreborrows.size()>0){
            busiBookBaseinfoMapper.updateBusiBookBaseinfoState(bookids,0);
            busiBookPreborrowMapper.updateBusiBookPreborrowState(preBorrowIds,2);
        }
    }

    public int currentPreBorrowNum(Long userId){
        return 0;
    }
}
