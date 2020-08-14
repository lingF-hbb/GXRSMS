package com.whx.gxrsms.interceptor;


import com.whx.gxrsms.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName AuthorityInterceptor
 * @Description 用户权限拦截器
 * @Version 1.0
 **/
public class AuthorityInterceptor implements HandlerInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(AuthorityInterceptor.class);

    private final UserService userService;

    public AuthorityInterceptor(UserService userService) {
        this.userService = userService;
    }

    /**
     *
     * 功能描述: 拦截访问修改页面的请求，如果非管理员身份，提示error.jsp
     * @param: HttpServletRequest,HttpServletResponse,Objerct
     * @return: boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (userService.ifUserIsAdmin(httpServletRequest.getSession())) {
            logger.info("管理员访问:" + httpServletRequest.getRequestURI());
            return true;
        }
        logger.info("非管理员访问:" + httpServletRequest.getRequestURI() + " 无操作权限");
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/error/error.html");
        return false;
    }
}
