package com.ruoyi.web.controller.busi;

import java.util.List;
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
import com.ruoyi.busi.domain.BusiBookBaseinfo;
import com.ruoyi.busi.service.IBusiBookBaseinfoService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 图书管理Controller
 * 
 * @author ruoyi
 * @date 2021-03-19
 */
@Controller
@RequestMapping("/busi/bookbaseinfo")
public class BusiBookBaseinfoController extends BaseController
{
    private String prefix = "busi/bookbaseinfo";

    @Autowired
    private IBusiBookBaseinfoService busiBookBaseinfoService;

    @RequiresPermissions("busi:bookbaseinfo:view")
    @GetMapping()
    public String bookbaseinfo()
    {
        return prefix + "/bookbaseinfo";
    }

   /* @RequiresPermissions("busi:bookselect:view")
    @GetMapping()
    public String bookselect()
    {
        return prefix + "/bookselect";
    }
*/
    /**
     * 查询图书管理列表
     */
    @RequiresPermissions("busi:bookbaseinfo:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BusiBookBaseinfo busiBookBaseinfo)
    {
        startPage();
        List<BusiBookBaseinfo> list = busiBookBaseinfoService.selectBusiBookBaseinfoList(busiBookBaseinfo);
        return getDataTable(list);
    }

    /**
     * 导出图书管理列表
     */
    @RequiresPermissions("busi:bookbaseinfo:export")
    @Log(title = "图书管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BusiBookBaseinfo busiBookBaseinfo)
    {
        List<BusiBookBaseinfo> list = busiBookBaseinfoService.selectBusiBookBaseinfoList(busiBookBaseinfo);
        ExcelUtil<BusiBookBaseinfo> util = new ExcelUtil<BusiBookBaseinfo>(BusiBookBaseinfo.class);
        return util.exportExcel(list, "bookbaseinfo");
    }

    /**
     * 新增图书管理
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存图书管理
     */
    @RequiresPermissions("busi:bookbaseinfo:add")
    @Log(title = "图书管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BusiBookBaseinfo busiBookBaseinfo)
    {
        return toAjax(busiBookBaseinfoService.insertBusiBookBaseinfo(busiBookBaseinfo));
    }

    /**
     * 修改图书管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BusiBookBaseinfo busiBookBaseinfo = busiBookBaseinfoService.selectBusiBookBaseinfoById(id);
        mmap.put("busiBookBaseinfo", busiBookBaseinfo);
        return prefix + "/edit";
    }

    /**
     * 修改保存图书管理
     */
    @RequiresPermissions("busi:bookbaseinfo:edit")
    @Log(title = "图书管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BusiBookBaseinfo busiBookBaseinfo)
    {
        return toAjax(busiBookBaseinfoService.updateBusiBookBaseinfo(busiBookBaseinfo));
    }

    /**
     * 图书信息详情
     */
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, ModelMap mmap)
    {
        BusiBookBaseinfo busiBookBaseinfo = busiBookBaseinfoService.selectBusiBookBaseinfoById(id);
        mmap.put("busiBookBaseinfo", busiBookBaseinfo);
        return prefix + "/detail";
    }

    /**
     * 删除图书管理
     */
    @RequiresPermissions("busi:bookbaseinfo:remove")
    @Log(title = "图书管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(busiBookBaseinfoService.deleteBusiBookBaseinfoByIds(ids));
    }
}
