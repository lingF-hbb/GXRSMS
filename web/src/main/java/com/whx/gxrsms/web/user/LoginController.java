package com.whx.gxrsms.web.user;

import com.whx.gxrsms.base.BaseController;
import com.whx.gxrsms.bean.Result;
import com.whx.gxrsms.exception.AuthenticationException;
import com.whx.gxrsms.model.User;
import com.whx.gxrsms.service.UserService;
import com.whx.gxrsms.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Objects;


/**
 * @project : HRM
 * @description : 控制器-管理登录页面
 */

@Slf4j
@Controller
public class LoginController extends BaseController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 跳转login页面
     * @param
     * @return
     */
    @RequestMapping(value = "/user/login.html", method = RequestMethod.GET)
    public String turnPage() {
        return "user/login" ;
    }

    @RequestMapping("/index.html")
    public String index() {
        User user = (User) super.getHttpServletRequest().getSession().getAttribute("user");
        if (Byte.valueOf("1").equals(user.getIsAdmin())) {
            return "/index_admin";
        }
        return "index";
    }

    /**
     * 功能描述: 登录验证
     *
     * @param: username, password, remember
     * @return: JsonMsg
     */
    @RequestMapping("/login")
    @ResponseBody
    public Result loginCheck(String username, String password, @RequestParam(value = "remember", required = false) String remember) {
        Result result = Result.success();
        log.info("用户登录：获取用户信息,登录参数为：username=[{}],password=[{}]]", username, password);

        try {
            User user = userService.login(username, password);
            if (Objects.nonNull(user)) {
                getHttpServletRequest().getSession().setAttribute("user", user);
                CookieUtil.setUserLoginCookie(user.getUsername(), user.getPassword(), remember,
                        super.getHttpServletRequest(), super.getHttpServletResponse());
            }
        } catch (AuthenticationException e) {
            result.setFail(e.getMessage());
        }
        return result;
    }

    /**
     * 功能描述: 账号登出
     *
     * @param: HttpSession, HttpServletResponse
     * @return: void
     */
    @RequestMapping("/layout")
    public void loginOut(HttpSession session, HttpServletResponse response) {
        session.removeAttribute("user");

        //注销后重定向到登录页面
        try {
            response.sendRedirect("user/login.html");
        } catch (IOException e) {
            System.out.println("跳转错误");
        }
    }
}
