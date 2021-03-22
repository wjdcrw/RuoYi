package com.ruoyi.web.controller.busi;

import com.ruoyi.busi.service.IBusiBookBorrowService;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 图书预借、借、还管理
 */
@Controller
@RequestMapping("/busi/bookborrow")
public class BusiBookBorrowController extends BaseController {
    private String prefix = "busi/bookborrow";
    @Autowired
    private IBusiBookBorrowService borrowService;

    @RequiresPermissions("busi:bookbaseinfo:preBorrow")
    @Log(title="图书预借",businessType = BusinessType.PREBORROW)
    @PostMapping( "/preBorrow/{id}")
    @ResponseBody
    public AjaxResult preBorrow(@PathVariable("id") Long id){
        return toAjax(borrowService.preBorrowBookById(id));
    }
}
