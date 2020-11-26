package com.yunhan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yunhan.entity.Role;
import com.yunhan.entity.User;

import java.util.Map;

public interface RoleService extends IService<Role> {
    Role getRoleById(String id);

    //验证角色名是否存在
    Integer getRoleNameCount(String roleName);

    //加载动态权限，配置权限验证规则
    Map<String,String> loadFilterChainDefinitions();

    //重新加载动态权限，配置权限验证规则
    void reloadFilterChainDefinitions();

    void updateRole(Role role);

    void deleteRole(Role role);

    Role saveRole(Role role);  //添加角色
}
