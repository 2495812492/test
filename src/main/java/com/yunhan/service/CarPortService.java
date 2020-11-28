package com.yunhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yunhan.entity.CarPort;

import java.util.List;

public interface CarPortService extends IService<CarPort> {
    //通过map集合中的参数查询房间列表信息
    List<CarPort> getPortListPage(Integer page, Integer limit, CarPort carPort);

    //分页查询房间总记录数
    Long getCountPortListPage(CarPort carPort);

    //通过id查询CarPort
    CarPort getCarPortById(CarPort carPort);

}
