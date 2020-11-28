package com.yunhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yunhan.entity.GmdLou;

public interface GmdLouService extends IService<GmdLou> {
    //通过id 修改一条数据
    public int upLou(GmdLou gmdLou);
}
