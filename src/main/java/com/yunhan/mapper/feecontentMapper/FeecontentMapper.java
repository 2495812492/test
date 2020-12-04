package com.yunhan.mapper.feecontentMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunhan.entity.Feecontent;

import java.util.List;
import java.util.Map;

public interface FeecontentMapper extends BaseMapper<Feecontent> {
    //通过map集合中的参数查询收费项目列表信息
    List<Feecontent> getFeeListPage(Map<String, Object> map);

    //分页查询收费项目总记录数
    Long getCountFeeListPage(Feecontent payItems);

    void addFee(Feecontent feecontent);

    List<Feecontent> getFeeListByFeeID(Integer feeID);

    Integer deleteFee(Integer feeID);

}
