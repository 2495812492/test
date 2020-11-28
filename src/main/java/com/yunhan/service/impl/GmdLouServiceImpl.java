package com.yunhan.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhan.entity.GmdLou;
import com.yunhan.mapper.gmdlouMapper.GmdLouMapper;
import com.yunhan.service.GmdLouService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("gmdLouService")
@Transactional(rollbackFor = Exception.class)
public class GmdLouServiceImpl extends ServiceImpl<GmdLouMapper, GmdLou> implements GmdLouService {

    @Override
    public int upLou(GmdLou gmdLou) {
        return baseMapper.upLou(gmdLou);
    }
}
