package com.whx.gxrsms.interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 禁止直接访问jsp页面
 * @author ZhaoShuai
 * @date Create in 2020/5/4
 **/
public class JspInterceptor extends HandlerInterceptorAdapter {

    private static final String JSP_SUFFIX = ".jsp";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        return !uri.endsWith(JSP_SUFFIX);
    }
}
