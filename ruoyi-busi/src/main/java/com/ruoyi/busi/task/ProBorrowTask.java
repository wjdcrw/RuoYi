package com.ruoyi.busi.task;

import com.ruoyi.busi.domain.BusiBookPreborrow;
import com.ruoyi.busi.service.IBusiBookBaseinfoService;
import com.ruoyi.busi.service.IBusiBookPreborrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("proBorrowTask")
public class ProBorrowTask {
    @Autowired
    IBusiBookPreborrowService busiBookPreborrowService;
    public void execute(){
        busiBookPreborrowService.preBorrowOverTimer();
    }
}
