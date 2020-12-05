package com.yunhan.controller;

import com.yunhan.common.base.PageData;
import com.yunhan.entity.Receivable;
import com.yunhan.entity.Receivables;
import com.yunhan.entity.pageCount;
import com.yunhan.entity.tableResult;
import com.yunhan.service.ReceivableService;
import com.yunhan.service.ReceivablesService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/*
  应收账单
 */
@Controller
public class ReceivableController {
    @Resource
    private ReceivablesService receivablesService;

    @RequestMapping("receivables")
    public String Receivables(){
        return "receivables";
    }

    @RequestMapping("/listReceivables")
    @ResponseBody
    public PageData<Receivables> listDate(
                @RequestParam(value = "page", defaultValue = "1") Integer page,
                @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                Receivables receivables){
        PageData<Receivables> receivableDate = new PageData<Receivables>();
        Long count = receivablesService.selectCount(receivables);
        List<Receivables> receivableList = receivablesService.selectAll(page,limit,receivables);
        receivableDate.setData(receivableList);
        receivableDate.setCount(count);
        return receivableDate;
    }

    /*@RequestMapping("listDataReceivable")
    @ResponseBody
    public tableResult<Map> listData(pageCount pageCount, Receivable receivable){
        tableResult<Map> result=new tableResult<>();
        List<Map> list=receivableService.selectAll(pageCount,receivable);
        //设置表格有多少条数据
        int count=receivableService.selectCount(receivable);
        result.setData(list);
        result.setCount(count);
        return result;
    }*/
}
