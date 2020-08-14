package com.whx.gxrsms.web.user;

import com.whx.gxrsms.bean.Result;
import com.whx.gxrsms.model.User;
import com.whx.gxrsms.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @project : HRM
 * @description : 控制器-管理注册页面
 */

@Controller
@RequestMapping("registe")
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    /**
     *
     * 功能描述: 跳转注册账号页面
     *
     * @param: null
     */
    @RequestMapping("toRegiste.html")
    public String toRegiste(){return "user/registe";}

    /**
     *
     * 功能描述: 注册用户
     *
     * @param: user
     * @return: Map<String,Object>
     */
    @RequestMapping("userRegiste.html")
    @ResponseBody
    public Result userRegiste(User user){
        Result result = Result.success();
        try {
            userService.userRegiste(user);
        } catch (Exception e) {
            result.setFail(e.getMessage());
        }

       return result;
    }

}
