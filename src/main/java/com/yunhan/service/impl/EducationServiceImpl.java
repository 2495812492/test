package com.yunhan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhan.entity.Educations;
import com.yunhan.mapper.educationMapper.EducationMapper;
import com.yunhan.service.EducationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("educationService")
@Transactional(rollbackFor = Exception.class)
public class EducationServiceImpl extends ServiceImpl<EducationMapper, Educations> implements EducationService {

}
