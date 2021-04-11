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
import com.ruoyi.busi.domain.BusiBill;
import com.ruoyi.busi.service.IBusiBillService;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 账单管理Controller
 * 
 * @author ruoyi
 * @date 2021-04-11
 */
@Controller
@RequestMapping("/busi/bill")
public class BusiBillController extends BaseController
{
    private String prefix = "busi/bill";

    @Autowired
    private IBusiBillService busiBillService;

    @RequiresPermissions("busi:bill:view")
    @GetMapping()
    public String bill()
    {
        return prefix + "/bill";
    }

    /**
     * 查询账单管理列表
     */
    @RequiresPermissions("busi:bill:list")
    @PostMapping("/list")
    @ResponseBody
    public TableDataInfo list(BusiBill busiBill)
    {
        startPage();
        List<BusiBill> list = busiBillService.selectBusiBillList(busiBill);
        return getDataTable(list);
    }

    /**
     * 导出账单管理列表
     */
    @RequiresPermissions("busi:bill:export")
    @Log(title = "账单管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    @ResponseBody
    public AjaxResult export(BusiBill busiBill)
    {
        List<BusiBill> list = busiBillService.selectBusiBillList(busiBill);
        ExcelUtil<BusiBill> util = new ExcelUtil<BusiBill>(BusiBill.class);
        return util.exportExcel(list, "bill");
    }

    /**
     * 新增账单管理
     */
    @GetMapping("/add")
    public String add()
    {
        return prefix + "/add";
    }

    /**
     * 新增保存账单管理
     */
    @RequiresPermissions("busi:bill:add")
    @Log(title = "账单管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    @ResponseBody
    public AjaxResult addSave(BusiBill busiBill)
    {
        return toAjax(busiBillService.insertBusiBill(busiBill).intValue());
    }

    /**
     * 修改账单管理
     */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, ModelMap mmap)
    {
        BusiBill busiBill = busiBillService.selectBusiBillById(id);
        mmap.put("busiBill", busiBill);
        return prefix + "/edit";
    }

    /**
     * 修改保存账单管理
     */
    @RequiresPermissions("busi:bill:edit")
    @Log(title = "账单管理", businessType = BusinessType.UPDATE)
    @PostMapping("/edit")
    @ResponseBody
    public AjaxResult editSave(BusiBill busiBill)
    {
        return toAjax(busiBillService.updateBusiBill(busiBill));
    }

    /**
     * 删除账单管理
     */
    @RequiresPermissions("busi:bill:remove")
    @Log(title = "账单管理", businessType = BusinessType.DELETE)
    @PostMapping( "/remove")
    @ResponseBody
    public AjaxResult remove(String ids)
    {
        return toAjax(busiBillService.deleteBusiBillByIds(ids));
    }
}
