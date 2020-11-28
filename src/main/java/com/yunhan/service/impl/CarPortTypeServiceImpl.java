package com.yunhan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhan.entity.CarportType;
import com.yunhan.mapper.carPortTypeMapper.CarPortTypeMapper;
import com.yunhan.service.CarPortTypeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("carPortTypeService")
@Transactional(rollbackFor = Exception.class)
public class CarPortTypeServiceImpl extends ServiceImpl<CarPortTypeMapper, CarportType> implements CarPortTypeService {
}
