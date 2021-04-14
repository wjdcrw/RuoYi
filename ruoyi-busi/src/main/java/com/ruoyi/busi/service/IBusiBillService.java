package com.ruoyi.busi.service;

import java.util.List;

import com.alipay.api.AlipayApiException;
import com.ruoyi.busi.domain.BusiBill;
import com.ruoyi.common.core.domain.AjaxResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 账单管理Service接口
 * 
 * @author ruoyi
 * @date 2021-03-29
 */
public interface IBusiBillService 
{
    /**
     * 查询账单管理
     * 
     * @param id 账单管理ID
     * @return 账单管理
     */
    public BusiBill selectBusiBillById(Long id);

    /**
     * 查询账单管理列表
     * 
     * @param busiBill 账单管理
     * @return 账单管理集合
     */
    public List<BusiBill> selectBusiBillList(BusiBill busiBill);

    /**
     * 新增账单管理
     * 
     * @param busiBill 账单管理
     * @return id
     */
    public Long insertBusiBill(BusiBill busiBill);

    /**
     * 修改账单管理
     * 
     * @param busiBill 账单管理
     * @return 结果
     */
    public int updateBusiBill(BusiBill busiBill);

    /**
     * 批量删除账单管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteBusiBillByIds(String ids);

    /**
     * 删除账单管理信息
     * 
     * @param id 账单管理ID
     * @return 结果
     */
    public int deleteBusiBillById(Long id);

    public AjaxResult payDepositQrCode(BusiBill busiBill);
    public void payDepositSuccess(HttpServletRequest request,
                                  HttpServletResponse response) throws AlipayApiException;

    public AjaxResult reDeposit(BusiBill busiBill);
    /**
     * 缴纳罚金
     * @param busiBill
     * @return
     */
    public AjaxResult payPenaltyQrCode(BusiBill busiBill);

    public void payPenaltySuccess(HttpServletRequest request,
                                  HttpServletResponse response);

    public AjaxResult rePenalty(BusiBill busiBill);
}
