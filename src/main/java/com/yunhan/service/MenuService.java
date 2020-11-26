package com.yunhan.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yunhan.entity.Menu;
import com.yunhan.entity.ShowMenuVo;
import com.yunhan.entity.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface MenuService extends IService<Menu> {
    Object getObj(QueryWrapper<Menu> parent_id);

    //通过各个条件查询菜单列表信息
    List<Menu> selectAllMenuList(Map<String,Object> map);

    //查询当前用户所拥有的菜单权限
    List<ShowMenuVo> getShowMenuByUser(String userId);

    //通过菜单ID查询菜单信息
    Menu selectById(String parentId);
    //通过菜单名称查询记录数，主要是对菜单名做唯一性验证所用。
    Integer getCountByName(String name);
    //根据权限标识查询记录数，主要是用来对权限标识做唯一性验证所用
    Integer getCountByPermission(String permission);
    //查询一级菜单中sort属性的最大值
    Integer selectFirstLevelMenuMaxSort();
    //根据父级菜单ID，查询其子级菜单列表中sort属性的最大值
    Integer seleclMenuMaxSortByPArentId(String parentId);
    //保存或更新菜单对象信息
    void saveOrUpdateMenu(Menu menu);

    List<Menu> getAllMenuList(Map<String,Object> map);
}
