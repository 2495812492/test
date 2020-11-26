package com.yunhan.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yunhan.common.annotation.SysLog;
import com.yunhan.common.base.PageData;
import com.yunhan.common.shiro.MyShiroRealm;
import com.yunhan.common.util.Encodes;
import com.yunhan.common.util.ResponseEntity;
import com.yunhan.entity.Role;
import com.yunhan.entity.User;
import com.yunhan.service.RoleService;
import com.yunhan.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("admin/system/user")
public class UserController {
    @Resource
    UserService userService;
    @Resource
    RoleService roleService;

    @GetMapping("/changePassword")
    public String changePassword(ModelMap modelMap){
        //获取登录的用户对象保存到ModelMap中，key为currentUser
        modelMap.put("currentUser", Encodes.getLoginUser());
        return "admin/user/changePassword";//跳转到密码修改页面
        //这个changePassword.html密码修改页面会被加载到index.html页面中的iframe
    }

    /*
     * 处理密码修改请求
     */
    @SysLog("用户修改密码")
    @PostMapping("changePassword")
    @ResponseBody
    public ResponseEntity changePassword(@RequestParam(value = "oldPwd",required = false)String oldPwd,
                                         @RequestParam(value = "newPwd",required = false)String newPwd){
        //得到登录的用户对象
        User usr = Encodes.getLoginUser();
        User user = userService.getById(usr.getId());
        Md5Hash md5Hash = new Md5Hash(oldPwd,user.getSalt(),1);
        String password = md5Hash.toString();
        if(!user.getPassword().equals(password)){
            return ResponseEntity.failure("旧密码错误");
        }
        //对新密码进行盐值加密
        Md5Hash md5 = new Md5Hash(newPwd,user.getSalt(),1);
        user.setPassword(md5.toString());
        userService.updateById(user);//调用MybatisPlus中预定义的updateById实现修改操作
        return ResponseEntity.success("操作成功");
    }

    @GetMapping("userinfo")
    public String toEditMyInfo(ModelMap modelMap){
        User user = Encodes.getLoginUser();
        user = userService.findUserById(user.getId());
        modelMap.put("userinfo",user);
        //3、user.getRoleLists()得到当前登录用户所涉及到的角色列表信息，再保存ModelMap
        modelMap.put("userRole",user.getRoleLists());
        return "admin/user/userInfo";
    }

    @SysLog("上传头像")
    @PostMapping("/uploadFace")
    @ResponseBody
    public ResponseEntity uploadFile(@RequestParam("icon") MultipartFile file){
        if(file == null){
            return ResponseEntity.failure("上传文件为空！");
        }
        String url = null;
        Map map = new HashMap();
        try{
            url = userService.upload(file);
            map.put("url",url);
            map.put("name",file.getOriginalFilename());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.failure(e.getMessage());
        }
        return ResponseEntity.success("操作成功").setAny("data",map);
    }

    @SysLog("系统用户个人信息修改")
    @PostMapping("saveUserinfo")
    @ResponseBody
    public ResponseEntity saveUserInfo(User user, HttpSession session){
        if(StringUtils.isBlank(user.getId())){
            return ResponseEntity.failure("用户ID不能为空");
        }
        if(StringUtils.isBlank(user.getLoginName())){
            return ResponseEntity.failure("登录名不能为空");
        }
        User oldUser = userService.findUserById(user.getId());
        if(StringUtils.isNotBlank(user.getEmail())){
            if(!user.getEmail().equals(oldUser.getEmail())){
                if(userService.userCount(user.getEmail())){
                    return ResponseEntity.failure("该邮箱已被使用");
                }
            }
        }
        if(StringUtils.isNotBlank(user.getTel())){
            if(!user.getTel().equals(oldUser.getTel())) {
                if (userService.userCount(user.getTel())) {
                    return ResponseEntity.failure("该手机号已经被绑定");
                }
            }
        }
        userService.updateById(user);
        return ResponseEntity.success("操作成功");
    }

    @GetMapping("list")
    @SysLog("跳转系统用户列表页面")
    public String list(){
        return "admin/user/list";
    }

    @RequiresPermissions("sys:user:list")
    @PostMapping("list")
    @ResponseBody
    public PageData<User> list(
//page参数为当前页码，  limit参数为每页显示的数据行数
            @RequestParam(value = "page",defaultValue = "1")Integer page,
            @RequestParam(value = "limit",defaultValue = "10")Integer limit,
            ServletRequest request){
        Map map = WebUtils.getParametersStartingWith(request, "s_");
        PageData<User> userPageData = new PageData< User >();
        QueryWrapper<User> userWrapper = new QueryWrapper<>();
        userWrapper.eq("del_flag",false);
        if(!map.isEmpty()){
            String type = (String) map.get("type");
            if(StringUtils.isNotBlank(type)) {
                userWrapper.eq("is_admin", "admin".equals(type) ? true : false);
            }
            String keys = (String) map.get("key");
            if(StringUtils.isNotBlank(keys)) {
                userWrapper.and(wrapper -> wrapper.like("login_name", keys).or().like("tel", keys).or().like("email", keys));
            }
        }
        IPage<User> userPage = userService.page(new Page<>(page,limit),userWrapper);
        userPageData.setCount(userPage.getTotal());
        userPageData.setData(userPage.getRecords());
        return userPageData;
    }

    //负责跳转到用户添加页面
    @GetMapping("add")
    public String add(ModelMap modelMap){
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("del_flag",false);
        //查询所有的角色列表信息
        List<Role> roleList = roleService.list(wrapper);
        modelMap.put("roleList",roleList);
        return "admin/user/add";
    }

    @SysLog("保存新增系统用户数据")
    @RequiresPermissions("sys:user:add")
    @PostMapping("add")
    @ResponseBody
    public ResponseEntity add(@RequestBody User user){
        if(StringUtils.isBlank(user.getLoginName())){
            return ResponseEntity.failure("登录名不能为空");
        }
        if(user.getRoleLists() == null || user.getRoleLists().size() == 0){
            return  ResponseEntity.failure("用户角色至少选择一个");
        }
        if(userService.userCount(user.getLoginName())){
            return ResponseEntity.failure("登录名称已经存在");
        }
        if(StringUtils.isBlank(user.getPassword())){
            return ResponseEntity.failure("密码不能为空");
        }
        if(StringUtils.isNotBlank(user.getEmail())){
            if(userService.userCount(user.getEmail())){
                return ResponseEntity.failure("该邮箱已被使用");
            }
        }
        if(StringUtils.isNoneBlank(user.getTel())){
            if(userService.userCount(user.getTel())){
                return ResponseEntity.failure("该手机号已被绑定");
            }
        }
        Encodes.entryptPassword(user);
        User u = Encodes.getLoginUser();
        user.setCreateDate(new Date());
        user.setCreateUser(u);
        user.setCreateId(u.getId());
        userService.saveUser(user);
        if(StringUtils.isBlank(user.getId())){
            return ResponseEntity.failure("保存用户信息出错");
        }
        //保存用户角色关系
        userService.saveUserRoles(user.getId(),user.getRoleLists());
        return ResponseEntity.success("操作成功");
    }

    @SysLog("锁定或开启系统用户")
    @RequiresPermissions("sys:user:lock")
    @PostMapping("lock")
    @ResponseBody
    public ResponseEntity lock(@RequestParam(value = "id",required = false)String id){
        if(StringUtils.isBlank(id)){
            return ResponseEntity.failure("参数错误");
        }
        User user = userService.getById(id);
        if(user == null){
            return ResponseEntity.failure("用户不存在");
        }
        if(user.getLocked()){
            user.setLocked(false);
        }else{
            user.setLocked(true);
        }
        userService.updateById(user);
        return ResponseEntity.success("操作成功");
    }

    //负责跳转到用户修改页面
    //添加处理"/admin/system/user/edit?id="+data.id请求的方法
    //修改前通过用户ID查询当前需要进行修改用户的详情对象信息
    @GetMapping("edit")
    public String toEdit(@RequestParam("id")String id,Model model){
        //1.通过用户ID查询用户的详情对象信息
        User user = userService.findUserById(id);
        //2.取出当即前用户对象的所有角色ID值使用逗号进行分隔，拼接成字符串
        String roleIds = "";
        if(user != null){
            roleIds = user.getRoleLists().stream().map(role -> role.getId()).collect(Collectors.joining(","));
        }
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("del_flag",Boolean.FALSE);
        //3.查询sys_role角色信息表，将所有的角色信息查询出来
        List<Role> roleList = roleService.list(wrapper);
        //4、将相关数据保存到Model对象中。
        model.addAttribute("localuser",user);
        model.addAttribute("roleIds",roleIds);
        model.addAttribute("roleList",roleList);
        //5、返回用户修改页面的逻辑视图名，再借助layer.open()方法实现将用户修改页面在弹出窗口显示的效果
        return "admin/user/edit";
    }

    @SysLog("保存系统用户编辑数据")
    @RequiresPermissions("sys:user:edit")
    @PostMapping("edit")
    @ResponseBody
    public ResponseEntity edit(@RequestBody User user){
        if(StringUtils.isBlank(user.getId())){
            return ResponseEntity.failure("用户ID不能为空");
        }
        if(StringUtils.isBlank(user.getLoginName())){
            return ResponseEntity.failure("登录名不能为空");
        }
        if(user.getRoleLists() == null && user.getRoleLists().size() == 0){
            return ResponseEntity.failure("用户角色至少选择一个");
        }
        User oldUser = userService.findUserById(user.getId());
        if(StringUtils.isNotBlank(user.getEmail())){
            if(!user.getEmail().equals(oldUser.getEmail())){
                if(userService.userCount(user.getEmail())){
                    return ResponseEntity.failure("该邮箱已被使用");
                }
            }
        }
        if(StringUtils.isNotBlank(user.getLoginName())){
            if(!user.getLoginName().equals(oldUser.getLoginName())) {
                if (userService.userCount(user.getLoginName())) {
                    return ResponseEntity.failure("该登录名已存在");
                }
            }
        }
        if(StringUtils.isNotBlank(user.getTel())){
            if(!user.getTel().equals(oldUser.getTel())) {
                if (userService.userCount(user.getTel())) {
                    return ResponseEntity.failure("该手机号已经被绑定");
                }
            }
        }

        user.setIcon(oldUser.getIcon());
        if(StringUtils.isBlank(user.getId())){
            return ResponseEntity.failure("保存用户信息出错");
        }
        userService.updateUser(user);
        return ResponseEntity.success("操作成功");
    }

    @SysLog("删除系统用户数据（单个）")
    @RequiresPermissions("sys:user:delete")
    @PostMapping("delete")
    @ResponseBody
    public ResponseEntity delete(@RequestParam(value = "id",required = false)String id){
        if(StringUtils.isBlank(id)){
            return ResponseEntity.failure("参数错误");
        }
        User user = userService.getById(id);
        if(user == null){
            return ResponseEntity.failure("用户不存在");
        }else if(user.getAdminUser()) {
            return ResponseEntity.failure("不能删除后台用户");
        }
        userService.deleteUser(user);
        return ResponseEntity.success("操作成功");
    }

    @SysLog("删除系统用户数据（多个）")
    @RequiresPermissions("sys:user:delete")
    @PostMapping("deleteSome")
    @ResponseBody
    public ResponseEntity deleteSome(@RequestBody List<User> users){
        if(users == null && users.size() == 0){
            return ResponseEntity.failure("请选择需要删除的用户");
        }
        for (User u : users){
            if(u.getAdminUser()){
                return ResponseEntity.failure("不能删除超级管理员");
            }else{
                userService.deleteUser(u);
            }
        }
        return ResponseEntity.success("操作成功");
    }
}
