package com.yunhan.mapper.roleMapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yunhan.entity.Menu;
import com.yunhan.entity.Role;
import com.yunhan.entity.User;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface RoleMapper extends BaseMapper<Role> {
    Role selectRoleById(@Param("roleId") String roleId);

    void saveRoleMenus(@Param("roleId") String roleId,  @Param("menus") Set<Menu> menuSet);

    void saveUserRole(@Param("userId") String userId,@Param("roleId") String roleId);

    void dropRoleMenus(@Param("roleId") String roleId);

    void dropRoleUsers(@Param("roleId") String roleId);
}
