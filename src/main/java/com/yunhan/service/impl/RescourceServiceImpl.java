package com.yunhan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhan.entity.Rescource;
import com.yunhan.mapper.rescourceMapper.RescourceMapper;
import com.yunhan.service.RescourceService;
import org.springframework.stereotype.Service;

@Service("rescourceService")
public class RescourceServiceImpl extends ServiceImpl<RescourceMapper, Rescource> implements RescourceService {
}
