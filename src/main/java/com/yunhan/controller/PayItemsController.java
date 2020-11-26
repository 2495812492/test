package com.yunhan.controller;

import com.yunhan.common.base.PageData;
import com.yunhan.entity.PayItems;
import com.yunhan.entity.PayItemsT;
import com.yunhan.service.PayItemsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class PayItemsController {
    @Resource
    private PayItemsService payItemsService;

    //负责跳转到gpay.html收费列表页面
    @RequestMapping("gpay")
    public String gpay(){
        return "gpay";
    }

    @RequestMapping("/gpaylistDate")
    @ResponseBody
    public PageData<PayItems> listDate(
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "limit",defaultValue = "10")Integer limit,
            PayItems payItems){
        PageData<PayItems> payItemsPageData = new PageData<PayItems>();
        Long count = payItemsService.getCountPayListPage(payItems);
        //2、调用service的getPayListPage方法，实现分页查询收费项目列表信息
        List<PayItems> payList= payItemsService.getPayListPage(page, limit, payItems);
        payItemsPageData.setData(payList);
        payItemsPageData.setCount(count);
        return payItemsPageData;
    }

    @RequestMapping("addgpay")
    public String addgpay(){
        return "addgpay";
    }

}
