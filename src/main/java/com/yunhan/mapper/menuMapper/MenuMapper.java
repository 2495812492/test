package com.yunhan.mapper.menuMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunhan.entity.Menu;
import com.yunhan.entity.ShowMenuVo;

import java.util.List;
import java.util.Map;

public interface MenuMapper extends BaseMapper<Menu> {

    //按多条件查询菜单列表信息
    List<Menu> getMenus(Map<String,Object> map);

    //查询当前用户所拥有的菜单权限
    List<ShowMenuVo> selectShowMenuByUser(Map<String,Object> map);

}

