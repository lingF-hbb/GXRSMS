package com.whx.gxrsms.web.user;

import com.whx.gxrsms.base.BaseController;
import com.whx.gxrsms.bean.Result;
import com.whx.gxrsms.model.User;
import com.whx.gxrsms.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: zy
 * @Description:
 */

@RequestMapping("modify")
@Controller
public class ModifyController extends BaseController {

    private final UserService userService;

    public ModifyController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 功能描述: 跳转到用户信息修改页面
     *
     * @param: model, session
     * @return: String
     */
    @RequestMapping("toModifyInfo.html")
    public String toInfo(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("userInfo", user);
        return "user/userInfo";
    }

    /**
     * 功能描述: 跳转到用户密码修改界面
     *
     * @param: null
     * @return: String
     */
    @RequestMapping("toModifyPwd.html")
    public String toPwd() {
        return "user/userPwd";
    }

    /**
     * 功能描述: 跳转到用户邮箱修改界面
     *
     * @param: null
     * @return: String
     */
    @RequestMapping("toModifyEmail.html")
    public String toEmail(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("userInfo", user);
        return "user/userEmail";
    }


    /**
     * 功能描述: 上传用户头像
     *
     * @param: MultipartFile file,session
     * @return: map
     */
    @RequestMapping("uploadUserIcon.html")
    @ResponseBody
    public Result uploadUserIcon(@RequestParam(value = "file", required = false) MultipartFile file) {

        Result result = Result.success();
        result.setCode(0);

        String realFilePath = null;
        try {
            realFilePath = userService.uploadUserIcon(file, super.getHttpServletRequest());
            Map<String, Object> map = new HashMap<>();
            map.put("src", realFilePath);
            result.setData(map);
        } catch (Exception e) {
            result.setMsg(e.getMessage());
        }

        return result;
    }

    /**
     * 功能描述: 验证邮箱验证码是否正确
     *
     * @param: code, user, session
     * @return: map
     */
    @RequestMapping("emailCodeTest.html")
    @ResponseBody
    public Map<String, Object> emailCodeTest(@RequestParam(value = "code") String code, User user, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if (userService.getUserByEmail(user.getEmail()) == null) {
            map.put("data", "emailNotExist");
            return map;
        } else if (!userService.testEmailCode(code, session)) {
            map.put("data", "codeWrong");
            return map;
        } else {
            map.put("data", "codeCorrect");
            return map;
        }
    }

    @RequestMapping("setUserInfo.html")
    @ResponseBody
    public Map<String, Object> setUserInfo(@RequestBody User user, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", userService.setUserInfo(user, session));
        return map;
    }

    @RequestMapping("resetPassword.html")
    @ResponseBody
    public Map<String, Object> resetPwdByOld(@RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        User user = (User) session.getAttribute("user");
        map.put("data", userService.resetPwdByOld(user, oldPassword, newPassword));
        return map;
    }

    @RequestMapping("resetEmail.html")
    @ResponseBody
    public Map<String, Object> resetEmail(@RequestParam("oldEmail") String oldEmail, @RequestParam("newEmail") String newEmail, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        User user = (User) session.getAttribute("user");
        map.put("data", userService.resetEmail(user, oldEmail, newEmail));
        return map;
    }
}
