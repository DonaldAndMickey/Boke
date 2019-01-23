package com.feifan.configure;

import java.util.HashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.feifan.shiro.UserRelam;

/**
 * shrio 配置类
 * 
 * @author Donald 2019-01-17 21:29
 *
 */

@Configuration
public class ShiroConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShiroConfig.class);

    /**
     * 内置过滤器
     * 
     * @param securityManager
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        LOGGER.info("shiro 过滤器开始执行");
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
        // 配置shiro n内置过滤器
        Map<String, String> filterMap = new HashMap<>();
        /*
         *  anon:表示可以匿名使用。 
            authc:表示需要认证(登录)才能使用，没有参数 
            roles：参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，当有多个参数时，例如admins/user/**=roles["admin,guest"],每个参数通过才算通过，相当于hasAllRoles()方法。 
            perms：参数可以写多个，多个时必须加上引号，并且参数之间用逗号分割，例如/admins/user/**=perms["user:add:*,user:modify:*"]，当有多个参数时必须每个参数都通过才通过，想当于isPermitedAll()方法。 
            rest：根据请求的方法，相当于/admins/user/**=perms[user:method] ,其中method为post，get，delete等。 
            port：当请求的url的端口不是8081是跳转到schemal://serverName:8081?queryString,其中schmal是协议http或https等，serverName是你访问的host,8081是url配置里port的端口，queryString是你访问的url里的？后面的参数。 
            authcBasic：没有参数表示httpBasic认证 
            ssl:表示安全的url请求，协议为https 
            user:当登入操作时不做检查
         */
        // 设置login url
        factoryBean.setLoginUrl("/service/page/login");
        // 设置成功后的跳转的连接
        factoryBean.setSuccessUrl("/service/page/index");
        factoryBean.setUnauthorizedUrl("/service/error-400");
        filterMap.put("/web/**", "anon");
        // 静态资源处理
        filterMap.put("/js/**", "anon");
        filterMap.put("/css/**", "anon");
        filterMap.put("/images/**", "anon");
        filterMap.put("/font/**", "anon");
        // 当请求/shiro/logout时登
        filterMap.put("/service/logout", "logout");
        filterMap.put("/service/login", "anon");

        /*
         *  注意:这里的user是过滤器的一种,而下面roles[user]中的user是自定义的一种角色。
         *  注意:user拦截器既允许通过Subject.login()认证进入的用户访问;又允许通过rememberMe缓存进入的用户访问
         *  注意:authc拦截器既只允许通过Subject.login()认证进入的用户访问;不允许rememberMe缓存通过进入的用户访问
         */
        filterMap.put("/introduce.html", "user");
        filterMap.put("/rememberMe.html", "user");
        // 注意roles[user]这里的话,角色不要再用引号引起来了,直接写即可
        filterMap.put("/user.html", "authc,roles[user]");
        filterMap.put("/admin.html", "authc,roles[admin]");
        filterMap.put("/superadmin.html", "authc,roles[host,admin]");

        // 由于权限由上而下“就近”选择,所以一般将"/**"配置在最下面
        filterMap.put("/**", "anon");// 必须授权才能访问
        factoryBean.setFilterChainDefinitionMap(filterMap);
        LOGGER.info("shiro 过滤器结束执行");
        return factoryBean;
    }

    @Bean
    public DefaultWebSecurityManager getSecurityManager(UserRelam userRelam, SessionManager sessionManager,
        EhCacheManager cacheManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置Relam
        securityManager.setRealm(userRelam);
        // 自定义缓存实现，
        securityManager.setCacheManager(cacheManager);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean
    public HashedCredentialsMatcher getHashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");// MD5加密
        hashedCredentialsMatcher.setHashIterations(1024);// 迭代1024次
        return hashedCredentialsMatcher;
    }

    /**
     * 配置relam 以及凭证匹配器，也可以在里面重写CredentialsMatcher 方法
     * 
     * @param hashedCredentialsMatcher
     * @return
     */
    @Bean
    public UserRelam getUserRelam(HashedCredentialsMatcher hashedCredentialsMatcher) {
        UserRelam userRelam = new UserRelam();
        userRelam.setCredentialsMatcher(hashedCredentialsMatcher);
        return userRelam;
    }

    /** 配置shiro框架组件的生命周期管理对象 */
    @Bean
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 配置授权属性应用对象(在执行授权操作时需要用到此对象) 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
     * 
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor getAuthorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

    /** 配置负责为Bean对象(需要授权访问的方法所在的对象) 创建代理对象的Bean组件 */
    @Bean
    public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
        proxyCreator.setProxyTargetClass(true);
        return proxyCreator;
    }

    /** 使用缓存ehcachae */
    @Bean
    public EhCacheManager getEhCacheManager() {
        EhCacheManager ehCacheManager = new EhCacheManager();
        // 将ehcacheManager转换成shiro包装后的ehcacheManager对象
        // ehCacheManager.setCacheManager(cacheManager);
        // eheache 配置文件目录
        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return ehCacheManager;
    }

    /**
     * shiro session的管理
     * 
     * @return
     */
    @Bean
    public SessionManager sessionManager(SimpleCookie simpleCookie, SessionDAO sessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(100000);// ms
        // 设置sessionDao对session查询，在查询在线用户service中用到了
        // sessionManager.setSessionDAO(sessionDAO);
        // 配置session 的监听
        // Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        // listeners.add(new BDsess)
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setSessionIdCookieEnabled(true);
        // 设置cookie 中sessionid 的名称
        sessionManager.setSessionIdCookie(simpleCookie);
        return sessionManager;
    }

    @Bean
    public SessionDAO getSessionDAO() {
        return new MemorySessionDAO();
    }

    @Bean
    public SimpleCookie getSimpleCookie() {
        SimpleCookie simpleCookie = new SimpleCookie();
        simpleCookie.setName("jeesite.session.id");
        return simpleCookie;
    }

}
