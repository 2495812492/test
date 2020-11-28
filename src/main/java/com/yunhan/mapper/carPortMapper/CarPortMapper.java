package com.yunhan.mapper.carPortMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunhan.entity.CarPort;

import java.util.List;
import java.util.Map;

public interface CarPortMapper extends BaseMapper<CarPort> {
    //通过map集合中的参数查询车位列表信息
    List<CarPort> getCarPortListByMap(Map<String, Object> map);

    //分页查询车位总记录数
    Long getCountCarPortListPage(CarPort cartPort);

    //通过id查询CarPort
    CarPort getCarPortById(CarPort carPort);

}
