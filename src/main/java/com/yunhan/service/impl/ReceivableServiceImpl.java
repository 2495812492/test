package com.yunhan.service.impl;

import com.yunhan.entity.Receivable;
import com.yunhan.entity.pageCount;
import com.yunhan.mapper.receivable.ReceivableMapper;
import com.yunhan.service.ReceivableService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ReceivableServiceImpl implements ReceivableService {
    @Resource
    private ReceivableMapper receivableMapper;

   /* @Override
    public List<Receivable> selectAll(Integer page, Integer limit, Receivable receivable) {
        Integer begin = (page - 1) * limit;
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("begin", begin);
        map.put("end", limit);
        map.put("precinct",receivable.getPrecinct());
        return receivableMapper.selectAll(map);
    }*/

    @Override
    public List<Map> selectAll(pageCount pageCount, Receivable receivable) {
        int begin=pageCount.getLimit()*(pageCount.getPage()-1);
        int end=pageCount.getLimit()*pageCount.getPage()-begin;
        Map<String,Object> map=new HashMap<>();
        map.put("begin",begin);
        map.put("end",end);
        map.put("precinct",receivable.getPrecinct());
        return receivableMapper.selectAll(map);
    }

    @Override
    public int selectCount(Receivable receivable) {
        return receivableMapper.selectCount(receivable);
    }
}
