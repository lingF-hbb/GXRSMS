package com.whx.gxrsms.config;

import com.whx.gxrsms.interceptor.AuthorityInterceptor;
import com.whx.gxrsms.interceptor.LoginInterceptor;
import com.whx.gxrsms.interceptor.TokenInterceptor;
import com.whx.gxrsms.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ZhaoShuai
 * @company lihfinance.com
 * @date Create in 2020/2/22
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    private final UserService userService;

    public InterceptorConfig(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录认证拦截器
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/user/login.html","/static/**", "/registe/**","/forget/**","/error");

        // 权限认证拦截器
        registry.addInterceptor(new AuthorityInterceptor(this.userService))
                .addPathPatterns("/modify/**")
                .excludePathPatterns("/modify/toModifyPwd.html", "/modify/toModifyInfo.html", "/modify/toModifyEmail.html");

        // token认证拦截器
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:index.html");
    }
}

