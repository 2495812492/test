package com.yunhan.controller;

import com.yunhan.common.annotation.SysLog;
import com.yunhan.common.util.Encodes;
import com.yunhan.common.util.ResponseEntity;
import com.yunhan.entity.User;
import com.yunhan.service.UserService;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Controller
@RequestMapping("admin/system/user")
public class UserController {
    @Resource
    UserService userService;

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
        User user = Encodes.getLoginUser();
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
}
