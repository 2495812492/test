package com.yunhan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhan.entity.Menu;
import com.yunhan.entity.ShowMenuVo;
import com.yunhan.mapper.menuMapper.MenuMapper;
import com.yunhan.service.MenuService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public Object getObj(QueryWrapper<Menu> parent_id) {
        return baseMapper.selectOne(parent_id);
    }

    @Override
    public List<Menu> selectAllMenuList(Map<String, Object> map) {
        return baseMapper.getMenus(map);
    }

    @Override
    public List<ShowMenuVo> getShowMenuByUser(String id) {
        Map<String,Object> map = new HashMap();
        map.put("userId",id);
        map.put("parentId",null);
        return baseMapper.selectShowMenuByUser(map);
    }

    @Override
    public Menu selectById(String parentId) {
        return baseMapper.selectById(parentId);
    }

    @Override
    public Integer getCountByName(String name) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag",false);
        wrapper.eq("name",name);
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public Integer getCountByPermission(String permission) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag",false);
        wrapper.eq("permission",permission);
        return baseMapper.selectCount(wrapper);
    }

    @Override
    public Integer selectFirstLevelMenuMaxSort() {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        //查询一级菜单中最大的sort属性值
        Object o = getObj(wrapper.select("max(sort) as sort").isNull("parent_id"));
        return  o == null ? 1 : ((Menu)o).getSort() + 1;  //这个最大值基本上再 +1
    }

    //通过父级菜单ID查询父级菜单下的子级菜单列表中sort属性的最大值再+1，作为当前新增菜单sort值
    @Override
    public Integer seleclMenuMaxSortByPArentId(String parentId) {
        QueryWrapper<Menu> wrapper = new QueryWrapper<>();
        Object o = getObj(wrapper.select("max(sort) as sort").eq("parent_id",parentId));
        return  o == null ? 1 : ((Menu)o).getSort() + 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateMenu(Menu menu) {
        saveOrUpdate(menu);
    }

    @Override
    public List<Menu> getAllMenuList(Map<String, Object> map) {
        return baseMapper.selectByMap(map);
    }

}

