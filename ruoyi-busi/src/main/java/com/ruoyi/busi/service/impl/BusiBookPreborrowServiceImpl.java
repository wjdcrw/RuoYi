package com.ruoyi.busi.service.impl;

import java.util.*;

import com.ruoyi.busi.domain.BusiBookBaseinfo;
import com.ruoyi.busi.domain.Result;
import com.ruoyi.busi.mapper.BusiBookBaseinfoMapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShiroUtils;
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

        if(SecurityUtils.getSubject().hasRole("reader")){
            busiBookPreborrow.setUserId(ShiroUtils.getSysUser().getUserId());
        }
        List<BusiBookPreborrow> busiBookPreborrows = busiBookPreborrowMapper.selectBusiBookPreborrowList(busiBookPreborrow);
        return busiBookPreborrows;
    }

    /**
     * 新增预约
     * 
     * @param busiBookPreborrow 预约
     * @return 结果
     */
    @Override
    @Transactional
    public AjaxResult insertBusiBookPreborrow(BusiBookPreborrow busiBookPreborrow)
    {
        BusiBookBaseinfo busiBookBaseinfo = busiBookBaseinfoMapper.selectBusiBookBaseinfoById(busiBookPreborrow.getBookId());
        //若图书状态不为空闲，预约失败
        if(busiBookBaseinfo.getState()!=BusiBookBaseinfo.StateType.FREE.value()){
            return AjaxResult.error("请选择空闲状态的图书！！！");
        }
        //检查读者当前预约数
        if(currentPreBorrowNum(busiBookPreborrow.getUserId())>0){
            return AjaxResult.error("您最多只可预约一本图书！！！");
        }

        //检查读者当前押金

        //图书置为预约状态
        busiBookBaseinfo.setState(BusiBookBaseinfo.StateType.APPOINTMENT.value());
        busiBookBaseinfoMapper.updateBusiBookBaseinfo(busiBookBaseinfo);
        //预约表中插入数据
        busiBookPreborrowMapper.insertBusiBookPreborrow(busiBookPreborrow);
        return AjaxResult.success();
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

    /**
     * 取消预约
     * @param busiBookPreborrow
     * @return
     */
    @Override
    @Transactional
    public int cancle(BusiBookPreborrow busiBookPreborrow) {
        try{
            //根据预约id查询预约信息
            busiBookPreborrow = busiBookPreborrowMapper.selectBusiBookPreborrowById(busiBookPreborrow.getId());
            busiBookPreborrow.setState(BusiBookPreborrow.StateType.CANCLE.value());
            busiBookPreborrow.setFinishTime(DateUtils.getNowDate());
            //找出预约的图书编号
            List<Long> bookIds=new ArrayList<>();
            bookIds.add(busiBookPreborrow.getBookId());
            //将预约的图书更新为空闲状态
            busiBookBaseinfoMapper.updateBusiBookBaseinfoState(bookIds,BusiBookBaseinfo.StateType.FREE.value());
            //将预约信息更新为取消状态
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

    /**
     * 处理未完成状态并且超时的预约信息
     */
    @Override
    @Transactional
    public void preBorrowOverTimer() {
        //查询所有的未完成状态并且超时的预约信息
        List<BusiBookPreborrow> busiBookPreborrows = busiBookPreborrowMapper.selectBusiBookPreborrowOverTimerList();
        List<Long> bookids=new ArrayList<>();
        List<Long> preBorrowIds=new ArrayList<>();
        for(BusiBookPreborrow _busiBookPreborrow:busiBookPreborrows){
            bookids.add(_busiBookPreborrow.getBookId());
            preBorrowIds.add(_busiBookPreborrow.getId());
        }
        if(busiBookPreborrows.size()>0){
            //将这些预约的图书状态更新为空闲
            busiBookBaseinfoMapper.updateBusiBookBaseinfoState(bookids,BusiBookBaseinfo.StateType.FREE.value());
            //将这些预约信息更新为超时状态
            busiBookPreborrowMapper.updateBusiBookPreborrowState(preBorrowIds,BusiBookPreborrow.StateType.OVERTIME.value());
        }
    }

    /**
     * 查询用户预约的图书数量
     * @param userId
     * @return
     */
    public int currentPreBorrowNum(Long userId){
        BusiBookPreborrow busiBookPreborrow=new BusiBookPreborrow();
        busiBookPreborrow.setUserId(userId);
        busiBookPreborrow.setState(BusiBookPreborrow.StateType.UNFINISH.value());
        int count = busiBookPreborrowMapper.selectBusiBookPreborrowList(busiBookPreborrow).size();
        return count;
    }
}
