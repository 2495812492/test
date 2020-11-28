package com.yunhan.mapper.gmdlouMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunhan.entity.GmdLou;

public interface GmdLouMapper extends BaseMapper<GmdLou> {
    //通过id 修改一条数据
    public int upLou(GmdLou gmdLou);
}
