package com.ruoyi.busi.service.impl;

import java.util.List;

import com.ruoyi.busi.domain.BusiBookBaseinfo;
import com.ruoyi.busi.domain.BusiBookPreborrow;
import com.ruoyi.busi.domain.Result;
import com.ruoyi.busi.mapper.BusiBookBaseinfoMapper;
import com.ruoyi.busi.mapper.BusiBookPreborrowMapper;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.busi.mapper.BusiBookBorrowMapper;
import com.ruoyi.busi.domain.BusiBookBorrow;
import com.ruoyi.busi.service.IBusiBookBorrowService;
import com.ruoyi.common.core.text.Convert;

/**
 * 借阅查询Service业务层处理
 * 
 * @author ruoyi
 * @date 2021-03-29
 */
@Service
public class BusiBookBorrowServiceImpl implements IBusiBookBorrowService 
{
    @Autowired
    private BusiBookBorrowMapper busiBookBorrowMapper;
    @Autowired
    private BusiBookBaseinfoMapper busiBookBaseinfoMapper;
    @Autowired
    private BusiBookPreborrowMapper busiBookPreborrowMapper;
    /**
     * 查询借阅查询
     * 
     * @param id 借阅查询ID
     * @return 借阅查询
     */
    @Override
    public BusiBookBorrow selectBusiBookBorrowById(Long id)
    {
        return busiBookBorrowMapper.selectBusiBookBorrowById(id);
    }

    /**
     * 查询借阅查询列表
     * 
     * @param busiBookBorrow 借阅查询
     * @return 借阅查询
     */
    @Override
    public List<BusiBookBorrow> selectBusiBookBorrowList(BusiBookBorrow busiBookBorrow)
    {
        return busiBookBorrowMapper.selectBusiBookBorrowList(busiBookBorrow);
    }

    /**
     * 新增借阅查询
     * 
     * @param busiBookBorrow 借阅查询
     * @return 结果
     */
    @Override
    public int insertBusiBookBorrow(BusiBookBorrow busiBookBorrow)
    {

        return busiBookBorrowMapper.insertBusiBookBorrow(busiBookBorrow);
    }

    /*public Result insertBusiBookBorrow(BusiBookBorrow busiBookBorrow){
        Result result=new Result();
        BusiBookBasein busiBookBasein=busiBookBaseinfoMapper.selectBusiBookBaseinfoById(busiBookBorrow.getBookId());

        //若图书为预约状态，查询预约者是不是当前借阅用户，若是则修改预约状态
        if(busiBookBaseinfo.getState()==1){
            //查询用户处于预约未完成状态的书籍编号
            BusiBookPreborrow busiBookPreborrow = busiBookPreborrowMapper.selectBusiBookPreborrowByUserId(busiBookBorrow.getUserId());
            if(busiBookPreborrow==null||!busiBookPreborrow.getBookId().equals(busiBookBorrow.getBookId())){
                result.setState(Result.FAIL);
                result.setMessage("请选择图书状态为空闲的图书！！！");
                return result;
            }
            busiBookPreborrow.setFinishTime(DateUtils.getNowDate());
            busiBookPreborrow.setState(1);
            busiBookPreborrowMapper.updateBusiBookPreborrow(busiBookPreborrow);
        }
        //图书状态为非0非1时
        if(busiBookBaseinfo.getState()!=0&&busiBookBaseinfo.getState()!=1){

            result.setState(Result.FAIL);
            result.setMessage("请选择图书状态为空闲的图书！！！");
            return result;
        }
        //检查读者当前借阅数
        if(currentBorrowNum(busiBookBorrow.getUserLoginName())>0){
            result.setState(Result.FAIL);
            result.setMessage("您最多只可借阅三本图书！！！");
            return result;
        }
        busiBookBaseinfo.setState(2);
        //更新图书状态
        busiBookBaseinfoMapper.updateBusiBookBaseinfo(busiBookBaseinfo);
        //记录借阅信息
        busiBookBorrowMapper.insertBusiBookBorrow(BusiBookBasein);
        result.setState(Result.SUCCESS);
    }*/

    public Result expendBorrowPeriod(Long borrowId){
        Result result=new Result();
        BusiBookBorrow busiBookBorrow = busiBookBorrowMapper.selectBusiBookBorrowById(borrowId);
        int maxPeriod=35;
        int extendPeriod=busiBookBorrow.getBorrowPeriod()+7;
        if(extendPeriod>=maxPeriod){
            result.setState(Result.FAIL);
            result.setMessage("已达到最高借阅天数，无法续借！！！");
            return result;
        }
        if(busiBookBorrow.getState()!=0){
            result.setState(Result.FAIL);
            result.setMessage("已不再是借阅状态，无法续借！！！");
            return result;
        }

        busiBookBorrow.setBorrowPeriod(extendPeriod);
        busiBookBorrowMapper.updateBusiBookBorrow(busiBookBorrow);
        result.setState(Result.SUCCESS);
        return result;
    }

    public Result returnBook(Long borrowId){
        Result result=new Result();
        BusiBookBorrow busiBookBorrow = busiBookBorrowMapper.selectBusiBookBorrowById(borrowId);
        BusiBookBaseinfo busiBookBaseinfo=busiBookBaseinfoMapper.selectBusiBookBaseinfoById(busiBookBorrow.getBookId());
        busiBookBaseinfo.setState(0);
        busiBookBaseinfoMapper.updateBusiBookBaseinfo(busiBookBaseinfo);
        busiBookBorrow.setReturnDate(DateUtils.getNowDate());
        busiBookBorrow.setState(1);
        busiBookBaseinfoMapper.updateBusiBookBaseinfo(busiBookBaseinfo);
        result.setState(Result.SUCCESS);
        return result;
    }
    /**
     * 修改借阅查询
     * 
     * @param busiBookBorrow 借阅查询
     * @return 结果
     */
    @Override
    public int updateBusiBookBorrow(BusiBookBorrow busiBookBorrow)
    {
        return busiBookBorrowMapper.updateBusiBookBorrow(busiBookBorrow);
    }

    /**
     * 删除借阅查询对象
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteBusiBookBorrowByIds(String ids)
    {
        return busiBookBorrowMapper.deleteBusiBookBorrowByIds(Convert.toStrArray(ids));
    }

    /**
     * 删除借阅查询信息
     * 
     * @param id 借阅查询ID
     * @return 结果
     */
    @Override
    public int deleteBusiBookBorrowById(Long id)
    {
        return busiBookBorrowMapper.deleteBusiBookBorrowById(id);
    }
}
