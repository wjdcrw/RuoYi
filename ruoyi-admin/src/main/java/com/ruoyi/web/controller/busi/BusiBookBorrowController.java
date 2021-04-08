package com.ruoyi.web.controller.busi;

import java.util.List;

import com.ruoyi.busi.domain.BusiBookBaseinfo;
import com.ruoyi.busi.service.IBusiBookBaseinfoService;
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
import com.ruoyi.busi.domain.BusiBookBorrow;
import com.ruoyi.busi.service.IBusiBookBorrowService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 借阅查询Controller
 * 
 * @author ruoyi
 * @date 2021-03-29
 */
@Controller
@RequestMapping("/busi/borrow")
public class BusiBookBorrowController extends BaseController
{
    private String prefix = "busi/borrow";

    @Autowired
    private IBusiBookBorrowService busiBookBorrowService;
    @Autowired
    private IBusiBookBaseinfoService busiBookBaseinfoService;

    @RequiresPermissions("busi:borrow:view")
    @GetMapping()
    public String borrow()
    {
        return prefix + "/borrow";
    }

    @RequiresPermissions("busi:borrow:add")
    @GetMapping("/add/{bookId}")
    public String borrowAdd(@PathVariable("bookId") Long bookId, ModelMap mmap){
        BusiBookBaseinfo busiBookBaseinfo = busiBookBaseinfoService.selectBusiBookBaseinfoById(bookId);
        BusiBookBorrow busiBookBorrow=new BusiBookBorrow();
        busiBookBorrow.setBookId(bookId);
        busiBookBorrow.setBookName(busiBookBaseinfo.getName());
        mmap.put("busiBookBorrow", busiBookBorrow);
        return prefix + "/borrowAdd";
    }

    /**
     * 查询借阅查询列表
     */
    @RequiresPermissions("busi:borrow:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BusiBookBorrow busiBookBorrow)
    {
        startPage();
        List<BusiBookBorrow> list = busiBookBorrowService.selectBusiBookBorrowList(busiBookBorrow);
        return getDataTable(list);
    }

    /**
     * 导出借阅查询列表
     */
    @RequiresPermissions("busi:borrow:export")
    @Log(title = "借阅查询", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BusiBookBorrow busiBookBorrow)
    {
        List<BusiBookBorrow> list = busiBookBorrowService.selectBusiBookBorrowList(busiBookBorrow);
        ExcelUtil<BusiBookBorrow> util = new ExcelUtil<BusiBookBorrow>(BusiBookBorrow.class);
        return util.exportExcel(list, "borrow");
    }

    /**
     * 新增借阅查询
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存借阅查询
     */
    @RequiresPermissions("busi:borrow:add")
    @Log(title = "借阅查询", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BusiBookBorrow busiBookBorrow)
    {
        return busiBookBorrowService.insertBusiBookBorrow(busiBookBorrow);
    }

    /**
     * 修改借阅查询
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BusiBookBorrow busiBookBorrow = busiBookBorrowService.selectBusiBookBorrowById(id);
        mmap.put("busiBookBorrow", busiBookBorrow);
        return prefix + "/edit";
    }

    /**
     * 修改保存借阅查询
     */
    @RequiresPermissions("busi:borrow:edit")
    @Log(title = "借阅查询", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BusiBookBorrow busiBookBorrow)
    {
        return toAjax(busiBookBorrowService.updateBusiBookBorrow(busiBookBorrow));
    }

    /**
     * 延长借阅期限
     */
    @RequiresPermissions("busi:borrow:extend")
    @Log(title = "借阅查询", businessType = BusinessType.UPDATE)
    @PostMapping("/extend")
    @ResponseBody
    public AjaxResult expendBorrowPeriod(BusiBookBorrow busiBookBorrow)
    {
        return busiBookBorrowService.expendBorrowPeriod(busiBookBorrow);
    }
    /**
     * 删除借阅信息
     */
    @RequiresPermissions("busi:borrow:remove")
    @Log(title = "借阅查询", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(busiBookBorrowService.deleteBusiBookBorrowByIds(ids));
    }

    /**
     * 还书
     */
    @RequiresPermissions("busi:borrow:return")
    @Log(title = "借阅查询", businessType = BusinessType.DELETE)
    @PostMapping( "/return")
    @ResponseBody
    public AjaxResult returnBook(BusiBookBorrow busiBookBorrow)
    {
        return busiBookBorrowService.returnBook(busiBookBorrow);
    }
}
