package com.yunhan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhan.entity.CarPort;
import com.yunhan.mapper.carPortMapper.CarPortMapper;
import com.yunhan.service.CarPortService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("carPortService")
@Transactional(rollbackFor = Exception.class)
public class CarPortServiceImpl extends ServiceImpl<CarPortMapper, CarPort> implements CarPortService {

    /**
     * 分页查询房间档案列表信息
     * @param page 当前页码
     * @param limit 每页显示的数据行数
     * @param carPort 车位对象
     */
    @Override
    public List<CarPort> getPortListPage(Integer page, Integer limit, CarPort carPort) {
        Integer first = (page - 1) * limit;  //获得分页sql语句中起始位置偏移量
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("first", first);
        map.put("pageSize", limit);
        if (!StringUtils.isEmpty(carPort.getClient())) {
            map.put("clientname", carPort.getClient().getClientname());
        }
        return baseMapper.getCarPortListByMap(map);
    }

    //分页查询房间总记录数
    @Override
    public Long getCountPortListPage(CarPort carPort) {
        return baseMapper.getCountCarPortListPage(carPort);
    }

    @Override
    public CarPort getCarPortById(CarPort carPort) {
        return baseMapper.getCarPortById(carPort);
    }
}
