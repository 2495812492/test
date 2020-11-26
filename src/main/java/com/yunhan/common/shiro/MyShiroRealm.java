package com.yunhan.common.shiro;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yunhan.common.util.Constants;
import com.yunhan.common.util.Encodes;
import com.yunhan.entity.Menu;
import com.yunhan.entity.User;
import com.yunhan.mapper.userMapper.UserMapper;
import com.yunhan.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.CacheManagerAware;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MyShiroRealm extends AuthorizingRealm {
    @Resource
    private UserService userService;

    @Resource
    private UserMapper userMapper;

    @Resource
    private StringRedisTemplate stringRedisTemplate;  //是用于对redis中String类型的数据进行增删改查

    //创建计数器的key为shiro_login_count_  ，用来记录当前用户的登录次数
    private final String SHIRO_LOGIN_COUNT = "shiro_login_count_";

    //用户登录是否被锁定约一小时 redis的Key 前缀。
    //假如admin用户登录次数达到5次，就将此用户锁定1小时，
    //此时以shiro_is_lock_admin 为key,值为LOCK，存入到redis缓存数据库。
    private final String SHIRO_IS_LOCK = "shiro_is_lock_";

    //当在doLogin方法中，调用currentUser.login(token); 提交Shiro认证时，
    // 程序会进入到下面的doGetAuthenticationInfo方法，在此方法中使用AuthenticationToken对象去接收
    //currentUser.login(token);中提交过来的token对象信息。
    //说白了，在doGetAuthenticationInfo方法中的token形参中可以接收从页面上面传递过来的用户名和密码。
    //是用来进行登录认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        //1、AuthenticationToken强转为UsernamePasswordToken
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        //2、分别匹配保存到UsernamePasswordToken里的用户名和密码(注： 此时的用户名和密码是从doLogin方法中提交过来的)
        String usrName = token.getUsername();  //得到用户名
        //String password = new String(token.getPassword());  //得到密码。
        /**********START 密码登录失限制 START**********/
        ValueOperations<String,String> opsForValue = stringRedisTemplate.opsForValue();
        //1、创建计数器， 记录当前用户的登录次数
        opsForValue.increment(SHIRO_LOGIN_COUNT + usrName, 1);
        //此计数器key为shiro_login_count_admin，value值为1
        //2、在对计数器的value值进行累加后，判断这个计数器的value值是否大于5，即当前用户的登录次数是否超过5次
        if (Integer.parseInt(opsForValue.get(SHIRO_LOGIN_COUNT + usrName)) > 5) {
            //意味着当前用户登录的次数超过了5次， 立马此用户锁定一个小时
            opsForValue.set(SHIRO_IS_LOCK + usrName, "LOCK");
            //将用户名为usrName的用户进行锁定
            //设置当前用户被锁定的时候为1小时
            stringRedisTemplate.expire(SHIRO_IS_LOCK + usrName, 1, TimeUnit.HOURS);
            //TimeUnit.HOURS单位为小时
        }

        //3、对锁定的用户进行相应提示
        if ("LOCK".equals(opsForValue.get(SHIRO_IS_LOCK + usrName))) {
            //手动抛出DisabledAccountException账号被禁用异常
            throw new DisabledAccountException("由于输入错误次数大于5次，帐号1小时内已经禁止登录！");
        }
        /**********END  密码登录失限制  END**********/
        //3、调用Service层-->Dao层，根据用户名查询用户信息是否存在以及密码是否正确，来判断当前用户的合法性
        User user = userService.findUserByLoginName(usrName);
        //4、进行登录认证
        if (user == null) {
            //通过用户名查询用户信息，如果得到用户对象为空，就意味着当前用户名在数据库不存在。
            throw new UnknownAccountException("您输入的账号不存在，请核对后再输入 ！");
        }

        //登录认证通过后，通过当前登录用户的角色查询此角色所拥有的权限列表，实现动态授权。
        //将当前用户的权限列表缓存到redis数据库中
        String menusJoson = JSONArray.toJSONString(user.getMenus());
        opsForValue.set(Constants.SHIRO_MENUS_KEY, menusJoson);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                user,
                user.getPassword(),
                ByteSource.Util.bytes(user.getSalt()),  //以字符串"salt" 盐
                getName());
        return info;
    }

    //用来授权/权限管理
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("权限授权-->MyShiroRealm.doGetAuthorizationInfo()");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        // getPrimaryPrincipal()：得到登录认证方法返回值的第一个参数的值
        User user = Encodes.getLoginUser();
        Map map = new HashMap();
        map.put("id",user.getId());
        user = userMapper.selectUserByMap(map);
        Set<Menu> menus = user.getMenus();
        for(Menu menu : menus) {
            if (StringUtils.isNotEmpty(menu.getPermission())) {
                System.out.println("此用户具有的链接权限：" + menu. getPermission ());
                info.addStringPermission(menu. getPermission ());
            }
        }
        return info;
    }

    //清空当前认证用户权限缓存
    public void clearMyCachedAuthorizationInfo(){
        //clearCachedAuthorizationInfo(SecurityUtils.getSubject().getPrincipals());
        if(this.isAuthorizationCachingEnabled()){//权限缓存是否可用
            Cache<Object,AuthorizationInfo> cache = null;
            CacheManager cacheManager = this.getCacheManager();
            if(cacheManager != null){
                String cacheName = this.getAuthorizationCacheName();//获得权限缓存名称
                cache = cacheManager.getCache(cacheName);//获得权限缓存
            }
            if(cache != null){
                cache.clear();//清空
            }
        }
    }
}

