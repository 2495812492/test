package com.yunhan.mapper.userMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunhan.entity.Role;
import com.yunhan.entity.User;
import org.springframework.data.repository.query.Param;

import java.util.Map;
import java.util.Set;

public interface UserMapper extends BaseMapper<User> {
    //按登录名、用户名ID查询用户详细信息
    User selectUserByMap(Map<String,Object> map);
    //向sys_user_role表新增数据
    void saveUserRoles(@Param("userId")String id, @Param("roleIds") Set<Role> roles);
    //通过用户ID删除向sys_user_role表删除指定数据
    void dropUserRolesByUserId(@Param("userId")String id);
}
