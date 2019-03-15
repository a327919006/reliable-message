package com.cn.rmq.cms.shiro;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title:</p>
 * <p>Description:</p>
 *
 * @author Chen Nan
 * @date 2019/3/14.
 */
@Configuration
public class ShiroConfig {

    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(CmsShiroRealm cmsShiroRealm) {
        return new DefaultWebSecurityManager(cmsShiroRealm);
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(CmsShiroRealm cmsShiroRealm){
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(defaultWebSecurityManager(cmsShiroRealm));
        shiroFilter.setLoginUrl("/login");
        shiroFilter.setSuccessUrl("/index");
        shiroFilter.setUnauthorizedUrl("/denies.jsp");


        Map<String, String> filterMap = new HashMap<>();
        filterMap.put("/css/**/**", "anon");
        filterMap.put("/external/**/**", "anon");
        filterMap.put("/images/**/**", "anon");
        filterMap.put("/js/**/**", "anon");
        filterMap.put("/login", "anon");
        filterMap.put("/login/**", "anon");
        filterMap.put("/**", "authc");
        shiroFilter.setFilterChainDefinitionMap(filterMap);

        return shiroFilter;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(CmsShiroRealm cmsShiroRealm) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(defaultWebSecurityManager(cmsShiroRealm));
        return advisor;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

}
