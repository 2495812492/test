package com.yunhan.common.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.yunhan.entity.Menu;
import com.yunhan.service.MenuService;
import com.yunhan.service.UserService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//Shiro的配置类
@Configuration
public class ShiroConfig {

    @Value("${spring.redis.host}")
    private String host;  //主机ip

    @Value("${spring.redis.port}")
    private int port; //端口号

    @Value("${spring.redis.password}")
    private String password;  //密码

    @Value("${spring.redis.timeout}")
    private int timeout;

    //设置Thymeleaf模板引擎对于Shiro标签的支持，如此我们在html页面中就可以使用Shiro标签
    @Bean     //创建ShiroDialect的bean实例对象
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }

    @Resource
    private UserService userService;

    @Resource
    private MenuService menuService;

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter(org.apache.shiro.mgt.SecurityManager securityManager) {
        System.out.println("ShiroConfiguration.shiroFilter()：配置权限控制规则");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //1、必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //2、设置跳转到登录页面的链接
        shiroFilterFactoryBean.setLoginUrl("/toLogin");
        //3、设置登录成功后的跳转链接
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //4、设置未授权的跳转链接，即当判断用户没有访问权限时，跳转到403.html页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        //5、利用Shiro内置拦截器，设置不会被拦截的链接地址
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
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }


    //1、创建MyShiroReaml的bean实例对象
    //<bean id="myShiroRealm" class="com.ktjiaoyu.crm.shiro.MyShiroRealm"/>
    @Bean
    public MyShiroRealm myShiroRealm() {
        MyShiroRealm realm = new MyShiroRealm();
        //为MyShiroRealm开启缓存
        realm.setCachingEnabled(true);
        //为MyShiroRealm类开启用户登录认证的缓存
        realm.setAuthorizationCachingEnabled(true);
        realm.setAuthorizationCacheName("authorizationCache"); //设置MyShiroRealm类用户登录认证的缓存的名字、、
        //使用HashedCredentialMatcher替换掉SimpleCredentialMatcher
        realm.setCredentialsMatcher(credentialsMatcher());
        return realm;
    }

    //配置redis缓存
    @Bean
    public RedisCacheManager cacheManager(){
        RedisCacheManager cacheManager = new RedisCacheManager();
        cacheManager.setRedisManager(redisManager());
        cacheManager.setPrincipalIdFieldName("loginName");  //缓存名称
        cacheManager.setExpire(1800);  //缓存30分钟,以秒为单位
        return cacheManager;
    }

    //2、创建SecurityManager的bean实例对象，并将上面定义的Realm对象注入给这个SecurityManager
    @Bean
    public org.apache.shiro.mgt.SecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager ();
        manager.setRealm(myShiroRealm());   //将上面定义的Realm对象注入给这个SecurityManager
        // 自定义缓存实现 使用redis
        manager.setCacheManager(cacheManager());
        //自定义session管理 使用redis
        manager.setSessionManager(sessionManager());
        return manager;
    }


    //自定义一个redis的Session管理
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDao());
        return sessionManager;
    }

    @Bean
    public RedisManager redisManager(){
        RedisManager manager = new RedisManager();
        manager.setHost(host);
        manager.setPassword(password);
        manager.setPort(port);
        manager.setTimeout(timeout);
        return manager;
    }

    @Bean
    public RedisSessionDAO sessionDao(){
        RedisSessionDAO dao = new RedisSessionDAO();
        dao.setRedisManager(redisManager());
        return dao;
    }

    //创建HashedCredentialsMatcher的bean实例对象
    @Bean
    public HashedCredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定当前项目为：  使用md5算法进行加密
        credentialsMatcher.setHashAlgorithmName("md5");
        credentialsMatcher.setHashIterations(1);  //指定md5盐值加密的次数为1
        return credentialsMatcher;
    }

    /* https://blog.csdn.net/new_yao/article/details/100101085 *
    /*加上下面两个配置实现进入doGetAuthorizationInfo方法方便调试*/
    /**
     *  开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    /**
     * 开启aop注解支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(org.apache.shiro.mgt.SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}

