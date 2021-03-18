package com.ruoyi.web.controller.system;

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
import com.ruoyi.system.domain.BusiBookType;
import com.ruoyi.system.service.IBusiBookTypeService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 图书类型Controller
 * 
 * @author ruoyi
 * @date 2021-03-18
 */
@Controller
@RequestMapping("/system/booktype")
public class BusiBookTypeController extends BaseController
{
    private String prefix = "system/booktype";

    @Autowired
    private IBusiBookTypeService busiBookTypeService;

    @RequiresPermissions("system:booktype:view")
    @GetMapping()
    public String booktype()
    {
        return prefix + "/booktype";
    }

    /**
     * 查询图书类型列表
     */
    @RequiresPermissions("system:booktype:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BusiBookType busiBookType)
    {
        startPage();
        List<BusiBookType> list = busiBookTypeService.selectBusiBookTypeList(busiBookType);
        return getDataTable(list);
    }

    /**
     * 导出图书类型列表
     */
    @RequiresPermissions("system:booktype:export")
    @Log(title = "图书类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BusiBookType busiBookType)
    {
        List<BusiBookType> list = busiBookTypeService.selectBusiBookTypeList(busiBookType);
        ExcelUtil<BusiBookType> util = new ExcelUtil<BusiBookType>(BusiBookType.class);
        return util.exportExcel(list, "booktype");
    }

    /**
     * 新增图书类型
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存图书类型
     */
    @RequiresPermissions("system:booktype:add")
    @Log(title = "图书类型", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BusiBookType busiBookType)
    {
        return toAjax(busiBookTypeService.insertBusiBookType(busiBookType));
    }

    /**
     * 修改图书类型
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BusiBookType busiBookType = busiBookTypeService.selectBusiBookTypeById(id);
        mmap.put("busiBookType", busiBookType);
        return prefix + "/edit";
    }

    /**
     * 修改保存图书类型
     */
    @RequiresPermissions("system:booktype:edit")
    @Log(title = "图书类型", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BusiBookType busiBookType)
    {
        return toAjax(busiBookTypeService.updateBusiBookType(busiBookType));
    }

    /**
     * 删除图书类型
     */
    @RequiresPermissions("system:booktype:remove")
    @Log(title = "图书类型", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(busiBookTypeService.deleteBusiBookTypeByIds(ids));
    }
}
