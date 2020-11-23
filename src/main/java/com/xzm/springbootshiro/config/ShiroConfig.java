package com.xzm.springbootshiro.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.LinkedHashMap;

@Configuration
public class ShiroConfig {

    @Bean
    public SecurityManager securityManager(ShiroRealm shiroRealm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 配置 SecurityManager，并注入 shiroRealm
        securityManager.setRealm(shiroRealm);
        // 配置 shiro session管理器
        // securityManager.setSessionManager(sessionManager());
        // 配置 缓存管理类 cacheManager
        // securityManager.setCacheManager(cacheManager());
        // 配置 rememberMeCookie
        // securityManager.setRememberMeManager(rememberMeManager());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager){

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        // 设置 securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 设置登录的 url
        shiroFilterFactoryBean.setLoginUrl("/thymeleaf");

        // 设置登录成功后跳转的 url
        shiroFilterFactoryBean.setSuccessUrl("/index");

        // 设置未授权 url
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        // 设置一个空MAP
        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 设置免认证 url(每行一个，要按顺序向下写，判断的时候是按顺序来的，这个要注意)
        filterChainDefinitionMap.put("/","anon");  //首页
        filterChainDefinitionMap.put("/thymeleaf","anon");  //登入页
        filterChainDefinitionMap.put("/doLogin","anon");  //登入验证接口

        // 配置退出过滤器，其中具体的退出代码 Shiro已经替我们实现了
        filterChainDefinitionMap.put("/logout", "logout");

        // 除上以外所有 url都必须认证通过才可以访问，未通过认证自动访问 LoginUrl
        filterChainDefinitionMap.put("/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return shiroFilterFactoryBean;
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    /**
 　　* 开启shiro aop注解支持.
 　　* 使用代理方式;所以需要开启代码支持;
 　　*/
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
