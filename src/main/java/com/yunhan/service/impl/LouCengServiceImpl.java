package com.yunhan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhan.entity.Louceng;
import com.yunhan.mapper.loucengMapper.LouCengMapper;
import com.yunhan.service.LouCengService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("louCengService")
@Transactional(rollbackFor = Exception.class)
public class LouCengServiceImpl extends ServiceImpl<LouCengMapper, Louceng> implements LouCengService {
}
