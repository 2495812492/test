package com.yunhan.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yunhan.common.util.Encodes;
import com.yunhan.entity.Menu;
import com.yunhan.entity.Role;
import com.yunhan.entity.User;
import com.yunhan.mapper.roleMapper.RoleMapper;
import com.yunhan.service.MenuService;
import com.yunhan.service.RoleService;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Resource
    private MenuService menuService;

    @Override
    public Role getRoleById(String roleId) {
        return baseMapper.selectRoleById(roleId);
    }

    @Override
    public Integer getRoleNameCount(String roleName) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("name",roleName);
        return baseMapper.selectCount(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(Role role) {
        baseMapper.updateById(role);
        baseMapper.dropRoleMenus(role.getId());
        if(role.getMenuSet() != null && role.getMenuSet().size() > 0) {
            baseMapper.saveRoleMenus(role.getId(), role.getMenuSet());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Role role) {
        role.setDelFlag(true); //采用逻辑删除对角色进行删除操作，其实只要修改del_flag值
        baseMapper.updateById(role);
        baseMapper.dropRoleMenus(role.getId());
        baseMapper.dropRoleUsers(role.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role saveRole(Role role) {
        //1、调用BaseMapper中的新增方法，完成对角色对象的新增
        baseMapper.insert(role);
        User user = Encodes.getLoginUser();
        baseMapper.saveUserRole(user.getId(),role.getId());
        //2、完成对角所对应菜单信息的添加
        if(role.getMenuSet() != null && role.getMenuSet().size() > 0) {
            baseMapper.saveRoleMenus(role.getId(),role.getMenuSet());
        }
        return role;
    }

    public Map<String,String> loadFilterChainDefinitions(){//加载动态权限，配置权限验证规则
        //利用Shiro内置拦截器，设置不会被拦截的链接地址
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

        filterChainDefinitionMap.put("/","anon");
        filterChainDefinitionMap.put("/static/**","anon");
        filterChainDefinitionMap.put("/admin","anon");
        filterChainDefinitionMap.put("/admin/index","anon");
        filterChainDefinitionMap.put("/admin/login","anon");
        filterChainDefinitionMap.put("/toLogin","anon");
        /*filterChainDefinitionMap.put("/compere/toLogin","anon");*/
        filterChainDefinitionMap.put("/WX/list","anon");
        filterChainDefinitionMap.put("/WX/searchList","anon");
        filterChainDefinitionMap.put("/WX/updateFee","anon");
        filterChainDefinitionMap.put("/WX/addPayType","anon");
        filterChainDefinitionMap.put("/getCaptcha","anon");
        filterChainDefinitionMap.put("/anonCtrl/","anon");
        filterChainDefinitionMap.put("/api/**", "anon");
        filterChainDefinitionMap.put("/sysRole/test","anon");
        filterChainDefinitionMap.put("/systemLogout","authc");
        //filterChainDefinitionMap.put("/**","authc");

        //从数据库读取sys_menu权限表中的权限链接href以及permission设置链接权限(动态设置链接权限)
        //1、调用service获取所有的权限
        List<Menu> menus = menuService.selectAllMenuList(null);
        //2、遍历menus权限列表，取出sys_menus权限表中的权限链接url以及perms设置链接权限
        for (Menu menu : menus) {
            //put(权限链接, 链接权限) 遍历出来的效果为： put("/user/add", "perms[user:add]")
            filterChainDefinitionMap.put(menu.getHref(), "perms[" + menu.getPermission() + "]");
            //注意：要求put(key, value); 中的key不为null，为此到sys_menu表里把href字段的值修改为#
        }

        //除了上面配置的，其他地访问路径都要登录的情况下才能访问。
        filterChainDefinitionMap.put("/**", "authc");
        return filterChainDefinitionMap;
    }

    public void reloadFilterChainDefinitions(){//重新加载动态权限，配置权限验证规则
        try{
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            ServletContext servletContext = request.getSession().getServletContext();
            AbstractShiroFilter shiroFilter=(AbstractShiroFilter) WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext).getBean("shiroFilter");
            synchronized(shiroFilter){
                //获取过滤管理器
                PathMatchingFilterChainResolver filterChainResolver=(PathMatchingFilterChainResolver)shiroFilter.getFilterChainResolver();
                DefaultFilterChainManager manager=(DefaultFilterChainManager)filterChainResolver.getFilterChainManager();
                //清空初始权限配置
                 manager.getFilterChains().clear();
                //重新加载动态权限，配置权限验证规则
                Map<String,String>chains=loadFilterChainDefinitions();
                //System.out.println("chains-------"+chains);
                for(Map.Entry<String,String>entry:chains.entrySet()){
                    String url=entry.getKey();
                    String chainDefinition=entry.getValue().trim().replace(" ","");
                    manager.createChain(url,chainDefinition);
                }
                System.out.println("更新权限成功！！");
            }
        }catch(Exception e){
                e.printStackTrace();
        }
    }
}
