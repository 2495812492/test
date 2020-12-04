package com.yunhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yunhan.entity.Feecontent;

import java.util.List;

public interface FeecontentService extends IService<Feecontent> {
    //通过map集合中的参数查询收费项目列表信息
    List<Feecontent> getFeeListPage(Integer page, Integer limit, Feecontent feecontent);

    //分页查询收费项目总记录数
    Long getCountFeeListPage(Feecontent feecontent);

    Feecontent addFee(Feecontent feecontent);

    List<Feecontent> getFeeListByFeeID(Integer feeID);

    boolean deleteFee(Integer feeID);
}
