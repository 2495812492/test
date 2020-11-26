package com.yunhan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunhan.common.annotation.SysLog;
import com.yunhan.common.base.PageData;
import com.yunhan.common.shiro.MyShiroRealm;
import com.yunhan.common.util.Encodes;
import com.yunhan.common.util.ResponseEntity;
import com.yunhan.entity.Menu;
import com.yunhan.entity.Role;
import com.yunhan.entity.User;
import com.yunhan.service.MenuService;
import com.yunhan.service.RoleService;
import com.yunhan.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin/system/role")
public class RoleController {
    //负责跳转到templates/admin/role/list.html页面
    @GetMapping(value = "list")
    public String list(){
        return "admin/role/list";
    }

    @Resource
    RoleService roleService;

    @Resource
    UserService userService;

    @RequiresPermissions("sys:role:list")
    @PostMapping("list")
    @ResponseBody
    public PageData<Role> list(
            //page参数为当前页码，  limit参数为每页显示的数据行数
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "limit",defaultValue = "10")Integer limit,
            ServletRequest request){
        //当在list.html页面的查询文本框中输入查询内容后，点击[查询]按扭，会将输入的
        //查询内容作为请求参数传递过来，由于查询文本框<input name="s_key"
        Map map = WebUtils.getParametersStartingWith(request, "s_");//获取查询的内容
        PageData<Role> rolePageData = new PageData<>();
        QueryWrapper<Role> roleWrapper = new QueryWrapper<>();
        roleWrapper.eq("del_flag",false);
        if(!map.isEmpty()){
            String keys = (String) map.get("key");
            if(StringUtils.isNotBlank(keys)) {
                roleWrapper.like("name", keys);
            }
        }
        IPage<Role> rolePage = roleService.page(new Page<Role>(page,limit),roleWrapper);
        rolePageData.setCount(rolePage.getTotal());
        rolePageData.setData(setUserToRole(rolePage.getRecords()));
        return rolePageData;
    }

    private List<Role> setUserToRole(List<Role> roles){
        roles.forEach(r -> {
            if(StringUtils.isNotBlank(r.getCreateId())){
                User u = userService.findUserById(r.getCreateId());
                if(StringUtils.isBlank(u.getNickName())){
                    u.setNickName(u.getLoginName());
                }
                r.setCreateUser(u);
            }
            if(StringUtils.isNotBlank(r.getUpdateId())){
                User u  = userService.findUserById(r.getUpdateId());
                if(StringUtils.isBlank(u.getNickName())){
                    u.setNickName(u.getLoginName());
                }
                r.setUpdateUser(u);
            }
        });

        return roles;
    }

    @Resource
    private MenuService menuService;

    //负责处理/admin/system/role/edit?id=  地址的请求
    @GetMapping("edit")
    public String edit(String id, ModelMap modelMap){
        //getRoleById(id) 是通过角色ID查询角色详情对象信息
        Role role = roleService.getRoleById(id);
        String menuIds = null;  //多个菜单对象的ID值使用逗号进行分隔的，如 25,30,40
        if(role != null) {
            menuIds  = role.getMenuSet().stream().map(menu -> menu.getId()).collect(Collectors.joining(","));
        }
        Map<String,Object> map = new HashMap();
        map.put("parentId",null);
        map.put("isShow",Boolean.TRUE);
        map.put("del_flag",Boolean.FALSE);
        //根据Map中传递的两个参数查询所有的菜单列表
        List<Menu> menuList = menuService.selectAllMenuList(map);
        modelMap.put("role",role);
        modelMap.put("menuList",menuList);
        modelMap.put("menuIds",menuIds);
        return "admin/role/edit"; //跳转到角色修改页面 edit.html
    }

    @SysLog("保存编辑角色数据")
    @RequiresPermissions("sys:role:edit")
    @PostMapping("edit")
    @ResponseBody
    public ResponseEntity edit(@RequestBody Role role){
        if(StringUtils.isBlank(role.getId())){
            return ResponseEntity.failure("角色ID不能为空");
        }
        if(StringUtils.isBlank(role.getName())){
            return ResponseEntity.failure("角色名称不能为空");
        }
        // getRoleById方法：通过角色ID查询角色的详情信息
        Role oldRole = roleService.getRoleById(role.getId());
        if(!oldRole.getName().equals(role.getName())){
        //验证角色名是否存在
            if(roleService.getRoleNameCount(role.getName())>0){
                return ResponseEntity.failure("角色名称已存在");
            }
        }
        //调用updateRole方法实现对角色对象的更新操作
        roleService.updateRole(role);
        return ResponseEntity.success("操作成功");
    }

    @RequiresPermissions("sys:role:delete")
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除角色数据")
    public ResponseEntity delete(@RequestParam(value = "id",required = false)String id){
        if(StringUtils.isBlank(id)){
            return ResponseEntity.failure("角色ID不能为空");
        }
        //1、通过角色ID查询角色详情信息
        Role role = roleService.getRoleById(id);
        //2、再deleteRole方法
        roleService.deleteRole(role);
        return ResponseEntity.success("操作成功");
    }

    @SysLog("多选删除角色数据")
    @RequiresPermissions("sys:role:delete")
    @PostMapping("deleteSome")
    @ResponseBody
    public ResponseEntity deleteSome(@RequestBody List<Role> roles){
        if(roles == null || roles.size()==0){
            return ResponseEntity.failure("请选择需要删除的角色");
        }
        for (Role r : roles){
            roleService.deleteRole(r);
        }
        return ResponseEntity.success("操作成功");
    }

    //负责跳转到admin/role/add.html角色添加页面
    @GetMapping("add")
    public String add(ModelMap modelMap){
        Map<String,Object> map =  new HashMap();
        map.put("parentId",null); //使得第一查询时，能把一级菜单列表查询出来。
        map.put("isShow",false); //不会进行展开
        List<Menu> menuList = menuService.selectAllMenuList(map);
        modelMap.put("menuList",menuList);
        return "admin/role/add";
    }

    @SysLog("保存新增角色数据")
    @RequiresPermissions("sys:role:add")
    @PostMapping("add")
    @ResponseBody
    public ResponseEntity add(@RequestBody Role role){
        if(StringUtils.isBlank(role.getName())){
            return ResponseEntity.failure("角色名称不能为空");
        }
        if(roleService.getRoleNameCount(role.getName())>0){
            return ResponseEntity.failure("角色名称已存在");
        }
        User user = Encodes.getLoginUser();
        role.setCreateUser(user);
        role.setCreateDate(new Date());
        role.setCreateId(user.getId());
        roleService.saveRole(role);
        return ResponseEntity.success("操作成功");
    }

    @RequestMapping(value = "/reload")
    public String reload(){
        roleService.reloadFilterChainDefinitions();
        return "redirect:/index";//重定向到index页面
    }
}
