package com.whx.gxrsms.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author ZhaoShuai
 * @company lihfinance.com
 * @date Create in 2020/2/22
 **/
public class LoginInterceptor extends HandlerInterceptorAdapter {

    private Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        request.setCharacterEncoding("UTF-8");

        Object user = request.getSession().getAttribute("user");
        if (Objects.isNull(user)) {
            logger.info("认证失效,重新认证");

            response.reset();
            response.sendRedirect("/gxrsms/user/login.html");
            return false;
        }
        return true;
    }
}
