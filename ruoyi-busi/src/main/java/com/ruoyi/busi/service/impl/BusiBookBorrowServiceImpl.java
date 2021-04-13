package com.ruoyi.busi.service.impl;

import java.util.List;

import com.ruoyi.busi.domain.BusiBookBaseinfo;
import com.ruoyi.busi.domain.BusiBookPreborrow;
import com.ruoyi.busi.mapper.BusiBookBaseinfoMapper;
import com.ruoyi.busi.mapper.BusiBookPreborrowMapper;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShiroUtils;
import com.ruoyi.system.service.ISysUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.busi.mapper.BusiBookBorrowMapper;
import com.ruoyi.busi.domain.BusiBookBorrow;
import com.ruoyi.busi.service.IBusiBookBorrowService;
import com.ruoyi.common.core.text.Convert;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private ISysUserService userService;
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
     * @param busiBookBorrow 借阅
     * @return 借阅查询
     */
    @Override
    public List<BusiBookBorrow> selectBusiBookBorrowList(BusiBookBorrow busiBookBorrow)
    {
        if(SecurityUtils.getSubject().hasRole("reader")){
            busiBookBorrow.setUserId(ShiroUtils.getSysUser().getUserId());
        }
        return busiBookBorrowMapper.selectBusiBookBorrowList(busiBookBorrow);
    }

    /**
     * 新增借阅
     * 
     * @param busiBookBorrow 借阅查询
     * @return 结果
     */
    /*@Override
    public int insertBusiBookBorrow(BusiBookBorrow busiBookBorrow)
    {

        return busiBookBorrowMapper.insertBusiBookBorrow(busiBookBorrow);
    }*/

    @Override
    @Transactional
    public AjaxResult insertBusiBookBorrow(BusiBookBorrow busiBookBorrow){
        BusiBookBaseinfo busiBookBaseinfo = busiBookBaseinfoMapper.selectBusiBookBaseinfoById(busiBookBorrow.getBookId());
        //校验用户是否存在
        SysUser sysUser = userService.selectUserByLoginName(busiBookBorrow.getUserLoginName());
        if(sysUser==null){
            return AjaxResult.error("用户名不存在！！！");
        }
        //检测是否缴纳押金
        if(!sysUser.alreadyPayDeposit()){
            return AjaxResult.error("您尚未缴纳押金！！！");
        }

        busiBookBorrow.setUserId(sysUser.getUserId());

        //图书状态为非空闲和预约状态
        if(busiBookBaseinfo.getState()!=BusiBookBaseinfo.StateType.FREE.value()
                &&busiBookBaseinfo.getState()!=BusiBookBaseinfo.StateType.APPOINTMENT.value()){
            return AjaxResult.error("请选择空闲状态的图书！！！");
        }

        //检查读者当前借阅数
        busiBookBorrow.setState(BusiBookBorrow.StateType.UNRETURN.value());
        List<BusiBookBorrow> busiBookBorrows = busiBookBorrowMapper.selectBusiBookBorrowList(busiBookBorrow);
        if(busiBookBorrows.size()>BusiBookBorrow.MAXBORROWCOUNT){
            return AjaxResult.error("您最多只可借阅"+BusiBookBorrow.MAXBORROWCOUNT+"本图书！！！");
        }

        //若图书为预约状态，查询预约者是不是当前借阅用户，若是则修改预约状态
        if(busiBookBaseinfo.getState()==BusiBookBaseinfo.StateType.APPOINTMENT.value()){
            //查询用户处于预约未完成状态的预约信息
            BusiBookPreborrow busiBookPreborrow=new BusiBookPreborrow();
            busiBookPreborrow.setUserId(busiBookBorrow.getUserId());
            busiBookPreborrow.setState(BusiBookPreborrow.StateType.UNFINISH.value());
            List<BusiBookPreborrow> busiBookPreborrows = busiBookPreborrowMapper.selectBusiBookPreborrowList(busiBookPreborrow);
            //遍历预约信息，判断借阅的图书是否被借阅用户预订
            boolean isBorrowUser=false;
            for(BusiBookPreborrow preborrow:busiBookPreborrows){
                if(preborrow.getBookId().equals(busiBookBorrow.getBookId())){
                    isBorrowUser=true;
                    preborrow.setFinishTime(DateUtils.getNowDate());
                    preborrow.setState(1);
                    busiBookPreborrowMapper.updateBusiBookPreborrow(preborrow);
                }
            }
            if(!isBorrowUser){
                return AjaxResult.error("此图书已被预约！！！");
            }

        }

        busiBookBaseinfo.setState(BusiBookBaseinfo.StateType.BORROW.value());
        //更新图书状态
        busiBookBaseinfoMapper.updateBusiBookBaseinfo(busiBookBaseinfo);
        busiBookBorrow.setBorrowDate(DateUtils.getNowDate());
        //记录借阅信息
        busiBookBorrowMapper.insertBusiBookBorrow(busiBookBorrow);
        return AjaxResult.success();
    }

    /**
     * 延长借阅期限
     * @param
     * @return
     */
    @Override
    @Transactional
    public AjaxResult expendBorrowPeriod(BusiBookBorrow busiBookBorrow){
        //根据借阅id查询借阅信息
        busiBookBorrow = busiBookBorrowMapper.selectBusiBookBorrowById(busiBookBorrow.getId());
        //判断是否可以延期
        int extendPeriod=busiBookBorrow.getBorrowPeriod()+BusiBookBorrow.ONCEEXTEND;
        if(extendPeriod>BusiBookBorrow.MAXPERIOD){
            return AjaxResult.error("已达到最高借阅天数，无法续借！！！");
        }
        if(busiBookBorrow.getState()!=BusiBookBorrow.StateType.UNRETURN.value()){
            return AjaxResult.error("已不再是借阅状态，无法续借！！！");
        }
        //更新借阅期限
        busiBookBorrow.setBorrowPeriod(extendPeriod);
        busiBookBorrowMapper.updateBusiBookBorrow(busiBookBorrow);
        return AjaxResult.success();
    }

    @Override
    @Transactional
    public AjaxResult returnBook(BusiBookBorrow busiBookBorrow){
        //查询借阅信息
        busiBookBorrow = busiBookBorrowMapper.selectBusiBookBorrowById(busiBookBorrow.getId());

        //查询图书信息
        BusiBookBaseinfo busiBookBaseinfo=busiBookBaseinfoMapper.selectBusiBookBaseinfoById(busiBookBorrow.getBookId());
        if(!busiBookBaseinfo.getDeptId().equals(ShiroUtils.getSysUser().getDeptId())){
            return AjaxResult.error("您不可以归还其他部门的图书！！！");
        }
        //更新图书信息状态
        busiBookBaseinfo.setState(BusiBookBaseinfo.StateType.FREE.value());
        busiBookBaseinfoMapper.updateBusiBookBaseinfo(busiBookBaseinfo);
        //更新借阅信息
        busiBookBorrow.setReturnDate(DateUtils.getNowDate());
        busiBookBorrow.setState(BusiBookBorrow.StateType.RETURN.value());
        busiBookBorrowMapper.updateBusiBookBorrow(busiBookBorrow);
        return AjaxResult.success();
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
