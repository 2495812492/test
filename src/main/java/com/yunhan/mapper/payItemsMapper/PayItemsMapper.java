package com.yunhan.mapper.payItemsMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunhan.entity.PayItems;
import com.yunhan.entity.PayItemsT;

import java.util.List;
import java.util.Map;

public interface PayItemsMapper extends BaseMapper<PayItems>{
    //通过map集合中的参数查询收费项目列表信息
    List<PayItems> getPayListPage(Map<String, Object> map);

    //分页查询收费项目总记录数
    Long getCountPayListPage(PayItems payItems);
}
