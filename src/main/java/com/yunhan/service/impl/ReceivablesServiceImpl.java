package com.yunhan.service.impl;

import com.yunhan.entity.Receivable;
import com.yunhan.entity.Receivables;
import com.yunhan.mapper.receivable.ReceivablesMapper;
import com.yunhan.service.ReceivablesService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ReceivablesServiceImpl implements ReceivablesService {
    @Resource
    private ReceivablesMapper receivablesMapper;

    @Override
    public List<Receivables> selectAll(Integer page, Integer limit, Receivables receivables) {
        Integer begin = (page - 1) * limit;
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("begin", begin);
        map.put("end", limit);
        map.put("roomNO",receivables.getRoomNO());
        return receivablesMapper.selectAll(map);
    }

    @Override
    public Long selectCount(Receivables receivables) {
        return receivablesMapper.selectCount(receivables);
    }
}
