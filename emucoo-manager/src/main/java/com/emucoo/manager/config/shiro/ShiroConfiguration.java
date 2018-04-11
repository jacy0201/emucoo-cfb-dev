package com.emucoo.manager.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.emucoo.manager.config.redis.RedisCacheManager;
import com.emucoo.manager.config.redis.RedisManager;
import com.emucoo.manager.config.redis.RedisSessionDAO;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Shiro配置
 * @author fujg
 * Created by fujg on 2017/2/9.
 */
@Configuration
public class ShiroConfiguration {
	
	@Value("${spring.redis.cluster.nodes}")
    public String nodes;

    @Value("${spring.cluster.pool.maxIdel}")
    public int maxIdel;

    @Value("${spring.cluster.pool.maxTotal}")
    public int maxTotal;

    @Value("${spring.cluster.pool.timeout}")
    public int timeout;
	
	@Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
    
    /**
     * ShiroFilterFactoryBean 处理拦截资源文件问题。
     * 注意：单独一个ShiroFilterFactoryBean配置是或报错的，因为在
     * 初始化ShiroFilterFactoryBean的时候需要注入：SecurityManager
     *
     Filter Chain定义说明
     1、一个URL可以配置多个Filter，使用逗号分隔
     2、当设置多个过滤器时，全部验证通过，才视为通过
     3、部分过滤器可指定参数，如perms，roles
     *
     */
    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
    	 ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
         // 必须设置 SecurityManager
         shiroFilterFactoryBean.setSecurityManager(securityManager);

         // 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
         shiroFilterFactoryBean.setLoginUrl("/admin/login");
         // 登录成功后要跳转的链接
//         shiroFilterFactoryBean.setSuccessUrl("/admin/index");
         //未授权界面，注:注解方式验证权限不好使;
//         shiroFilterFactoryBean.setUnauthorizedUrl("/admin/403");

         //自定义登出过滤器，设置登出后跳转地址
         Map<String, Filter> filters = new LinkedHashMap<String, Filter>();

         //自定义退出跳转页面
         LogoutFilter logoutFilter = new LogoutFilter();
         logoutFilter.setRedirectUrl("/admin/login");
         filters.put("myLogout", logoutFilter);

         //filters.put("kickout", kickoutSessionControlFilter);
         shiroFilterFactoryBean.setFilters(filters);

         //过虑器链定义，从上向下顺序执行，一般将/**放在最下边
         Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
         //配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
         filterChainDefinitionMap.put("/admin/logout", "myLogout");
         //登录请求匿名访问
         filterChainDefinitionMap.put("/admin/login", "anon");

         //过滤链定义，从上向下顺序执行，一般将放在最为下边:这是一个坑呢，一不小心代码就不好使了;
         //authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问
         filterChainDefinitionMap.put("/admin/**", "authc");
         shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
         return shiroFilterFactoryBean;
    }


    @Bean
    public SecurityManager securityManager(){
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        //设置realm.
        securityManager.setRealm(authenticationRealm());
        // 自定义缓存实现 使用redis
        securityManager.setCacheManager(cacheManager());
        // 自定义session管理 使用redis
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public AuthenticationRealm authenticationRealm(){
    	AuthenticationRealm authenticationRealm = new AuthenticationRealm();
    	authenticationRealm.setCredentialsMatcher(hashedCredentialsMatcher());
    	
    	//配置缓存
        //开启缓存
        authenticationRealm.setCachingEnabled(true);
        //开启认证缓存
        //authenticationRealm.setAuthenticationCachingEnabled(true);
        //设置认证缓存的名称对应ehcache中配置
        authenticationRealm.setAuthenticationCacheName("shiro-activeSessionCache");
        //开启授权信息缓存
        authenticationRealm.setAuthorizationCachingEnabled(true);
        //设置授权缓存的名称对应ehcache中配置
        authenticationRealm.setAuthorizationCacheName("authorizationCache");
        return authenticationRealm;
    }

    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     *  所以我们需要修改下doGetAuthenticationInfo中的代码;
     * ）
     * @return
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
    	HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");//散列算法:这里使用MD5算法;
        credentialsMatcher.setHashIterations(1); //散列的次数，比如散列两次，相当于 md5(md5(""));
        credentialsMatcher.setStoredCredentialsHexEncoded(true);
        return credentialsMatcher;
    }


    /**
     *  开启shiro aop注解支持.
     *  使用代理方式;所以需要开启代码支持;
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     * @return
     */
    public RedisManager redisManager() {
        RedisManager redisManager = new RedisManager();
        redisManager.setNodes(nodes);
        redisManager.setMaxIdel(maxIdel);;
        redisManager.setMaxTotal(maxTotal);;
        redisManager.setTimeout(timeout);
        //redisManager.setPassword(password);
        return redisManager;
    }

    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }


    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }

    /**
     * shiro session的管理
     */
    @Bean(name = "sessionManager")
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
       
        //session的失效时长，单位毫秒
        sessionManager.setGlobalSessionTimeout(3600000);  //60分钟失效
        sessionManager.setSessionValidationScheduler(getExecutorServiceSessionValidationScheduler());
        sessionManager.setSessionValidationSchedulerEnabled(true);
        //删除失效的session
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionIdCookieEnabled(true);
        sessionManager.setSessionIdCookie(getSessionIdCookie());
        sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setCacheManager(cacheManager());
        //去除浏览器地址栏中url中JSESSIONID参数
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        
        return sessionManager;
    }
    
    /**
     * 会话验证调度器
     * @return
     */
    @Bean(name = "sessionValidationScheduler")
    public ExecutorServiceSessionValidationScheduler getExecutorServiceSessionValidationScheduler() {
        ExecutorServiceSessionValidationScheduler scheduler = new ExecutorServiceSessionValidationScheduler();
        scheduler.setInterval(3600000);//每隔60分钟验证清理失效的session
        return scheduler;
    }
    
    /**
     * 会话Cookie模板
     * @return
     */
    @Bean(name = "sessionIdCookie")
    public SimpleCookie getSessionIdCookie() {
        SimpleCookie cookie = new SimpleCookie("sid");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(-1);
        return cookie;
    }
    
    /**
     * ShiroDialect，为了在thymeleaf里使用shiro的标签的bean
     * @return
     */
    @Bean
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
    
}
