package com.yunhan.controller;

import com.yunhan.common.annotation.SysLog;
import com.yunhan.common.shiro.MyShiroRealm;
import com.yunhan.common.util.Constants;
import com.yunhan.common.util.Encodes;
import com.yunhan.common.util.ResponseEntity;
import com.yunhan.entity.ShowMenuVo;
import com.yunhan.entity.User;
import com.yunhan.service.MenuService;
import com.yunhan.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
public class LoginController {
    @Autowired
    private final static Logger LOGGER = LoggerFactory.getLogger(CaptchaController.class);
    @Autowired
    private UserService userService;
    public static final String LOGIN_TYPE = "admin";
    public static class LoginTypeEnum{
        public static final String ADMIN = "admin";
    }

    @RequestMapping({"/", "/toLogin"})
    public String toLogin() {
        return "admin/login";
    }

    //跳转到登录页面
    @GetMapping(value = "/toLogin")
    public String adminIndex() {
        Subject s = SecurityUtils.getSubject();
        //验证当前登录主体是否已经通过认证，通过认证则跳转到index.html首页
        if(s.isAuthenticated()) {
            return "redirect:admin/index";
        }
        //没有通过认证则重定向到toLogin地址
        return "admin/login";
    }

    /**
     * 处理用户登录的请求
     * @param loginName 从登录页面传递过来的用户名
     * @param password 从登录页面传递过来的密码
     * @param code 从登录页面传递过来的验证码
     */
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private final String SHIRO_LOGIN_COUNT = "shiro_login_count_";//用户登录次数计数

    @PostMapping("/admin/login")
    @SysLog("用户登录")
    @ResponseBody
    public ResponseEntity adminLogin(
            //从login.html页面传递过来的用户名
            @RequestParam("username")String username,
            //从login.html页面传递过来的密码
            @RequestParam("password")String password,
            @RequestParam("rememberMe")String rememberMe,@RequestParam("code")String code,
            HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session == null){
            return ResponseEntity.failure("session超时");
        }
        String trueCode = (String)session.getAttribute(Constants.VALIDATE_CODE);
        if(StringUtils.isBlank(trueCode)){
            return ResponseEntity.failure("验证码超时");
        }
        if(StringUtils.isBlank(code) || !trueCode.toLowerCase().equals(code.toLowerCase())){
            return ResponseEntity.failure("验证码错误");
        }else {
            /*当前用户*/
            ValueOperations<String, String> opsForValue = stringRedisTemplate.opsForValue();
            //1、获取登录主体对象
            Subject curruser = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(username,password,Boolean.valueOf(rememberMe));
            User user = null;
            try {
                //2、当使用登录主体对象调用login()登录方法时。它后续用户认证(登录)就会交给Shiro去操作。
                curruser.login(token);
                LOGGER.debug(username+"用户"+ LocalDate.now().toString()+":======》登陆系统!");
                //4、在MyShiroRealm类中的doGetAuthenticationInfo方法中进行登录用户认证，并返回AuthenticationInfo对象
                //其中AuthenticationInfo对象中保存三个参数，Object principal, Object credentials, String realmName
                user = (User) curruser.getPrincipal();  //可以到AuthenticationInfo对象中的第一个参数，即用户对象
                //user = userService.getUser(currentUser.getPrincipal().toString());
                //5、将登录认证成功后的用户对象保存到Sesssion作用域中,以"currentUser"为key
                session.setAttribute(Constants.CURRENT_USER, user);
                //清空权限缓存
                /*RealmSecurityManager realmSecurityManager = (RealmSecurityManager)SecurityUtils.getSecurityManager();
                MyShiroRealm realm = (MyShiroRealm)realmSecurityManager.getRealms().iterator().next();
                realm.clearMyCachedAuthorizationInfo();*/
                //当登录成功后，清空登录计数
                opsForValue.set(SHIRO_LOGIN_COUNT + username, "0");

                ResponseEntity responseEntity = new ResponseEntity();
                responseEntity.setSuccess(Boolean.TRUE);
                responseEntity.setAny("url","index");
                return responseEntity;
            } catch (IncorrectCredentialsException ic) {
                //已经登录的次数
                Integer reTryCount = Integer.parseInt(opsForValue.get(SHIRO_LOGIN_COUNT + username));
                //用户在页面上输入的密码不相等
                return ResponseEntity.failure("您输入的密码不正确，" +
                        "您还有次" + (5 - reTryCount) + "机会！");
            } catch (DisabledAccountException da) {  //登录次数达到5次，提示用户账号被锁。
                return ResponseEntity.failure(da.getMessage());
            } catch (Exception e) {
                return ResponseEntity.failure(e.getMessage());
            }
        }
    }

    //负责跳转到首页
    @GetMapping(value = "/index")
    public String index(HttpSession session) {
        //User principal = (User) SecurityUtils.getSubject().getPrincipal();
        User principal = Encodes.getLoginUser();
        session.setAttribute("icon", StringUtils.isBlank(principal.getIcon()) ? "/static/admin/img/face.jpg" : principal.getIcon());
        return "admin/index";
    }

    @Resource
    private MenuService menuService;

    /***
     * 获得用户所拥有的菜单列表
     * @return
     */
    @GetMapping("/admin/user/getUserMenu")
    @ResponseBody
    public List<ShowMenuVo> getUserMenu(){
        //User user = (User)SecurityUtils.getSubject().getPrincipal();
        User user = Encodes.getLoginUser();
        List<ShowMenuVo> list = menuService.getShowMenuByUser(user.getId());
        return list;
    }

    //在index.jsp首页中的右侧显示main.jsp这个欢迎页面
    @RequestMapping("/admin/main")
    public String main() {
        return "admin/main";
    }

    @GetMapping("/systemLogout")
    @SysLog("退出系统")
    public String logOut(){
        SecurityUtils.getSubject().logout();
        return "redirect:toLogin";
    }
}