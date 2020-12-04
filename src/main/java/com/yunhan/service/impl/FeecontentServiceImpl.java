package com.yunhan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhan.entity.Feecontent;
import com.yunhan.entity.PayItems;
import com.yunhan.mapper.feecontentMapper.FeecontentMapper;
import com.yunhan.service.FeecontentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("feecontentService")
@Transactional(rollbackFor = Exception.class)
public class FeecontentServiceImpl extends ServiceImpl<FeecontentMapper, Feecontent> implements FeecontentService {
    @Override
    public Long getCountFeeListPage(Feecontent feecontent) {
        return baseMapper.getCountFeeListPage(feecontent);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Feecontent addFee(Feecontent feecontent) {
        baseMapper.addFee(feecontent);
        return feecontent;
    }

    @Override
    public List<Feecontent> getFeeListByFeeID(Integer feeID) {
        return baseMapper.getFeeListByFeeID(feeID);
    }

    @Override
    public boolean deleteFee(Integer feeID) {
        if(baseMapper.deleteFee(feeID)>0){
            return true;
        }else {
            return false;
        }

    }

    @Override
    public List<Feecontent> getFeeListPage(Integer page, Integer limit, Feecontent feecontent) {
        Integer first = (page - 1) * limit;  //获得分页sql语句中起始位置偏移量
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("first", first);
        map.put("pageSize", limit);
        //按客户名称模糊查询
        if (!StringUtils.isEmpty(feecontent)) {
            map.put("clientname", feecontent.getClientname());
            map.put("collectDate", feecontent.getCollectDate());
        }
        return baseMapper.getFeeListPage(map);
    }
}
