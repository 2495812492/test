package com.yunhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yunhan.entity.PayItems;
import com.yunhan.entity.PayItemsT;

import java.util.List;

public interface PayItemsService extends IService<PayItems> {
    //通过map集合中的参数查询收费项目列表信息
    List<PayItems> getPayListPage(Integer page, Integer limit, PayItems payItems);

    //分页查询收费项目总记录数
    Long getCountPayListPage(PayItemsT payItems);
}
