package com.yunhan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhan.entity.PayItems;
import com.yunhan.entity.PayItemsT;
import com.yunhan.mapper.payItemsMapper.PayItemsMapper;
import com.yunhan.service.PayItemsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("payItemsService")
public class PayItemsServiceImpl extends ServiceImpl<PayItemsMapper, PayItems> implements PayItemsService {
    @Override
    public Long getCountPayListPage(PayItemsT payItems) {
        return baseMapper.getCountPayListPage(payItems);
    }

    @Override
    public List<PayItems> getPayListPage(Integer page, Integer limit, PayItems payItems) {
        Integer first = (page - 1) * limit;  //获得分页sql语句中起始位置偏移量
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("first", first);
        map.put("pageSize", limit);
        //按费用类型模糊查询
        if (!StringUtils.isEmpty(payItems.getPayItemsType())) {
            map.put("patitemstName", payItems.getPayItemsType().getPatitemstName());
        }
        return baseMapper.getPayListPage(map);
    }
}
