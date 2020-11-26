package com.yunhan.controller;

import com.yunhan.common.annotation.SysLog;
import com.yunhan.common.base.PageData;
import com.yunhan.common.shiro.MyShiroRealm;
import com.yunhan.common.util.Encodes;
import com.yunhan.common.util.ResponseEntity;
import com.yunhan.entity.Menu;
import com.yunhan.entity.User;
import com.yunhan.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("admin/system/menu")
public class MenuController {
    @Resource
    MenuService menuService;

    @SysLog("跳转菜单列表")
    @GetMapping("list")
    public String list(){
        return "admin/menu/list";
    }

    @RequiresPermissions("sys:menu:list")
    @RequestMapping("treeList")
    @ResponseBody
    public PageData<Menu> treeList(){
        PageData<Menu>  menuPageData = new PageData<>();
        HashMap map = new HashMap();
        map.put("del_flag",false);
        List<Menu> menus = menuService.getAllMenuList(map);
        for(Menu menu:menus){
            //遍历菜单列表，如果当前菜单对象的parentId父级ID为空，则设置它的值为-1
            if(menu.getParentId() == null) {
                menu.setParentId("-1");
            }
        }
        //实现按Menu实体类中的sort字段进行升序排序
        menus.sort(Comparator.comparing(Menu::getSort));
        menuPageData.setData(menus);
        return menuPageData;
    }

    //负责跳转到菜单添加页面 add.html
    @GetMapping("/add")
    public String add(@RequestParam(value = "parentId",required = false) String parentId, ModelMap modelMap){
        if(parentId != null){
            //如果父级菜单ID不为空，通过父级ID查询父级菜单的详情对象信息
            Menu menu = menuService.selectById(parentId);
            modelMap.put("parentMenu",menu);
        }
        return "admin/menu/add";
    }

    @RequiresPermissions("sys:menu:add")
    @PostMapping("add")
    @ResponseBody
    @SysLog("保存新增菜单数据")
    public ResponseEntity add(Menu menu){
        //对菜单名称作唯一性验证
        if(menuService.getCountByName(menu.getName())>0){
            return ResponseEntity.failure("菜单名称已存在");
        }
        //对权限标识(sys_menu表中permission)做唯一性验证
        if(StringUtils.isNotBlank(menu.getPermission())){
            if(menuService.getCountByPermission(menu.getPermission())>0){
                return ResponseEntity.failure("权限标识已经存在");
            }
        }
        //如果父级菜单ID为null
        if(menu.getParentId() == null){
            menu.setLevel(1);
            //selectFirstLevelMenuMaxSort方法用于查询一级菜单中sort属性的最大值
            menu.setSort(menuService.selectFirstLevelMenuMaxSort());
        }else{
            //通过父类ID查询菜单对象信息
            Menu parentMenu = menuService.selectById(menu.getParentId());
            if(parentMenu==null){
                return ResponseEntity.failure("父菜单不存在");
            }
            menu.setParentIds(parentMenu.getParentIds());
            menu.setLevel(parentMenu.getLevel()+1);
            //通过父级菜单ID查询父级菜单下的子级菜单列表中sort属性的最大值再+1，作为当前新增菜单sort值
            menu.setSort(menuService.seleclMenuMaxSortByPArentId(menu.getParentId()));
        }
        User user = Encodes.getLoginUser();
        menu.setCreateUser(user);
        menu.setCreateDate(new Date());
        menu.setCreateId(user.getId());


        //isBlank(menu.getParentIds())如果当前新增菜单的parentIds值为空，parentids值为当前菜单对象id
        menu.setParentIds(StringUtils.isBlank(menu.getParentIds()) ? menu.getId()+"," : menu.getParentIds() + menu.getId()+",");
        menuService.saveOrUpdateMenu(menu); //事先确保创建了MyMetaObjectHandler类
        return ResponseEntity.success("操作成功");
    }

    //负责通过菜单ID查询菜单对象信息后，再跳转到edit.html页面中显示菜单详情信息
    @GetMapping("/edit")
    public String edit(String id,ModelMap modelMap){
        Menu menu = menuService.selectById(id);
        modelMap.addAttribute("menu",menu);
        return "admin/menu/edit";
    }

    @RequiresPermissions("sys:menu:edit")
    @PostMapping("edit")
    @ResponseBody
    @SysLog("保存编辑菜单数据")
    public ResponseEntity edit(Menu menu){
        //菜单ID的非空验证
        if(StringUtils.isBlank(menu.getId())){
            return ResponseEntity.failure("菜单ID不能为空");
        }
        //菜单名称非空验证
        if (StringUtils.isBlank(menu.getName())) {
            return ResponseEntity.failure("菜单名称不能为空");
        }
        //通过菜单ID查询菜单详情对象信息
        Menu oldMenu = menuService.selectById(menu.getId());
        if(!oldMenu.getName().equals(menu.getName())) {
            //菜单名称的唯一性验证
            if(menuService.getCountByName(menu.getName())>0){
                return ResponseEntity.failure("菜单名称已存在");
            }
        }
        if (StringUtils.isNotBlank(menu.getPermission())) {
            if(!oldMenu.getPermission().equals(menu.getPermission())) {
                //权限标识的唯一性验证
                if (menuService.getCountByPermission(menu.getPermission()) > 0) {
                    return ResponseEntity.failure("权限标识已经存在");
                }
            }
        }
        if(menu.getSort() == null){
            return ResponseEntity.failure("排序值不能为空");
        }
        //调用saveOrUpdateMenu保存对菜单对象的修改操作
        menuService.saveOrUpdateMenu(menu);
        return ResponseEntity.success("操作成功");
    }

    @RequiresPermissions("sys:menu:delete")
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除菜单")
    public ResponseEntity delete(@RequestParam(value = "id",required = false)String id){
        if(StringUtils.isBlank(id)){
            return ResponseEntity.failure("菜单ID不能为空");
        }
        Menu menu = menuService.selectById(id);
        menu.setDelFlag(true);
        menuService.saveOrUpdateMenu(menu);
        return ResponseEntity.success("操作成功");
    }
}
