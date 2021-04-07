package com.ruoyi.web.controller.busi;

import java.io.Serializable;
import java.util.List;

import com.ruoyi.busi.service.IBusiBookBaseinfoService;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.busi.domain.BusiBookPreborrow;
import com.ruoyi.busi.service.IBusiBookPreborrowService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

import javax.security.auth.Subject;

/**
 * 预约查询Controller
 * 
 * @author ruoyi
 * @date 2021-03-28
 */
@Controller
@RequestMapping("/busi/preBorrow")
public class BusiBookPreborrowController extends BaseController
{
    private String prefix = "busi/preBorrow";

    @Autowired
    private IBusiBookPreborrowService busiBookPreborrowService;


    @RequiresPermissions("busi:preBorrow:view")
    @GetMapping()
    public String preBorrow()
    {
        return prefix + "/preBorrow";
    }

    /**
     * 查询预约查询列表
     */
    @RequiresPermissions("busi:preBorrow:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BusiBookPreborrow busiBookPreborrow)
    {
        startPage();
        List<BusiBookPreborrow> list = busiBookPreborrowService.selectBusiBookPreborrowList(busiBookPreborrow);
        return getDataTable(list);
    }

    /**
     * 导出预约查询列表
     */
    @RequiresPermissions("busi:preBorrow:export")
    @Log(title = "预约查询", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BusiBookPreborrow busiBookPreborrow)
    {
        List<BusiBookPreborrow> list = busiBookPreborrowService.selectBusiBookPreborrowList(busiBookPreborrow);
        ExcelUtil<BusiBookPreborrow> util = new ExcelUtil<BusiBookPreborrow>(BusiBookPreborrow.class);
        return util.exportExcel(list, "preBorrow");
    }

    /**
     * 新增预约查询
     */
//    @GetMapping("/add")
//    public String add()
//    {
//        return prefix + "/add";
//    }

    /**
     * 新增保存预约查询
     */
    @RequiresPermissions("busi:preBorrow:appointment")
    @Log(title = "预约查询", businessType = BusinessType.INSERT)
    @PostMapping("/appointment")
    @ResponseBody
    public AjaxResult appointment(Long bookId)
    {
        BusiBookPreborrow busiBookPreborrow=new BusiBookPreborrow();
        busiBookPreborrow.setBookId(bookId);
        busiBookPreborrow.setUserId(ShiroUtils.getSysUser().getUserId());
        busiBookPreborrow.setCreateTime(DateUtils.getNowDate());
        return busiBookPreborrowService.insertBusiBookPreborrow(busiBookPreborrow);
    }
    /**
     * 新增保存预约查询
     */
    @RequiresPermissions("busi:preBorrow:add")
    @Log(title = "预约查询", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(Long bookId)
    {
        BusiBookPreborrow busiBookPreborrow=new BusiBookPreborrow();
        busiBookPreborrow.setBookId(bookId);
        busiBookPreborrow.setUserId(ShiroUtils.getSysUser().getUserId());
        busiBookPreborrow.setCreateTime(DateUtils.getNowDate());
        return busiBookPreborrowService.insertBusiBookPreborrow(busiBookPreborrow);
    }

    /**
     * 修改预约查询
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BusiBookPreborrow busiBookPreborrow = busiBookPreborrowService.selectBusiBookPreborrowById(id);
        mmap.put("busiBookPreborrow", busiBookPreborrow);
        return prefix + "/edit";
    }

    /**
     * 修改保存预约查询
     */
    @RequiresPermissions("busi:preBorrow:edit")
    @Log(title = "预约查询", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BusiBookPreborrow busiBookPreborrow)
    {
        return toAjax(busiBookPreborrowService.updateBusiBookPreborrow(busiBookPreborrow));
    }
    /**
     * 取消预约
     */
    @RequiresPermissions("busi:preBorrow:remove")
    @Log(title = "取消查询", businessType = BusinessType.DELETE)
    @PostMapping( "/cancle")
    @ResponseBody
    public AjaxResult cancle(BusiBookPreborrow busiBookPreborrow){
        return toAjax(busiBookPreborrowService.cancle(busiBookPreborrow));
    }
    /**
     * 删除预约查询
     */
    @RequiresPermissions("busi:preBorrow:remove")
    @Log(title = "预约查询", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(busiBookPreborrowService.deleteBusiBookPreborrowByIds(ids));
    }
}
