package com.whx.gxrsms.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.*;

import com.whx.gxrsms.base.AbstractService;
import com.whx.gxrsms.enums.WorkEnum;
import com.whx.gxrsms.exception.AuthenticationException;
import com.whx.gxrsms.mapper.UserMapper;
import com.whx.gxrsms.model.User;
import com.whx.gxrsms.service.UserService;
import com.whx.gxrsms.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author ZhaoShuai
 * @company lihfinance.com
 * @date Create in 2020/2/23
 **/
@Slf4j
@Service
public class UserServiceImpl extends AbstractService<User> implements UserService {

    private static final String APP_PATH = System.getProperty("user.dir") + "/web/target/classes";

    @Resource
    private UserMapper userMapper;

    @Autowired
    private MailUtil mailUtil;

    @Value("${icon-path}")
    private String iconPath;

    @Override
    protected Mapper<User> getMapper() {
        return userMapper;
    }

    @Override
    public User login(String username, String password) throws AuthenticationException {
        User user = new User()
                .setUsername(username);
        user = userMapper.selectOne(user);
        if (Objects.isNull(user)) {
            throw new AuthenticationException("登录失败,用户不存在");
        }

        if (!user.getPassword().equals(SecurityUtil.encryptUserPassword(password, user.getUsername()))) {
            throw new AuthenticationException("登录失败,密码错误");
        }

        if (WorkEnum.STOP.getCode().equals(user.getStatus())) {
            throw new AuthenticationException("登录失败，用户已停用");
        }

        user.setPassword(null);
        return user;
    }

    @Override
    public void updateUserSession(Long id, HttpSession session) {
        User user = super.queryById(id);
        session.setAttribute("user", user);
    }

    @Override
    public void userRegiste(User user) {

        Assert.isNull(super.queryForOne(new User().setUsername(user.getUsername())), "用户名已存在");

        Assert.isNull(this.getUserByEmail(user.getEmail()), "邮箱地址已存在");

        Assert.isNull(super.queryForOne(new User().setPhone(user.getPhone())), "手机号码已存在");

        user.setPassword(SecurityUtil.encryptUserPassword(user.getPassword(), user.getUsername()));

        super.insert(user);
    }

    @Override
    public String emailCheck(String email) {
        if (Objects.nonNull(this.getUserByEmail(email))) {
            return "emailExist";
        }
        return "emailNotExist";
    }

    @Override
    public String uploadUserIcon(MultipartFile file, HttpServletRequest request) {

        String fileNameNew = null;
        StringBuilder builder = new StringBuilder(APP_PATH);
        builder.append(iconPath);
        try {
            String originalFilename = file.getOriginalFilename();
            Assert.notNull(originalFilename, "头像文件名不能为空");
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            builder.append("/");

            User user = (User) request.getSession().getAttribute("user");

            String date = DateUtil.formatString(new Date(), "yyyyMMddHHmmss");
            builder.append("user_").append(user.getUsername()).append("_icon").append(date).append(suffix);

            fileNameNew = FileUtil.upload(file, builder.toString());
        } catch (IOException e) {
            log.error("头像上传失败,失败文件：[{}]", file.getOriginalFilename());
        }
        Assert.notNull(fileNameNew, "文件名为空");
        return fileNameNew.substring(fileNameNew.indexOf("/static/imgs"));
    }

    @Override
    public User getUserByEmail(String email) {
        return super.queryForOne(new User().setEmail(email));
    }

    @Override
    public EmailVerifyCode sendCodeToEmail(String email) {
        String emailaddress = email;
        String code = VerifyCode.randomCode();
        String msg = "收到来自高校人事管理系统的验证码:\n" + code + "\n 有效时间:" + EmailVerifyCode.validTime / (1000 * 60) + "分钟";

        try {
            mailUtil.SendMail(emailaddress, msg);
            return new EmailVerifyCode(email, code, MyTimeUtil.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean testEmailCode(String code, HttpSession session) {
        EmailVerifyCode emailVerifyCode = (EmailVerifyCode) session.getAttribute("emailVerifyCode");
        if (code == null || !code.equals(emailVerifyCode.getCode()) || !emailVerifyCode.isValid()) {
            return false;
        } else return true;
    }

    @Override
    public String setUserInfo(User user, HttpSession session) {
        super.updateNotNullable(user);
        session.setAttribute("user", super.queryById(user.getId()));
        return "successReset";
    }

    @Override
    public String resetPwdByEmail(User user) {
        User userTemp = this.getUserByEmail(user.getEmail());
        userTemp.setPassword(SecurityUtil.encryptUserPassword(user.getPassword(), userTemp.getUsername()));

        userMapper.resetPwdByEmail(userTemp);
        return "resetSuccess";
    }

    @Override
    public String resetPwdByOld(User user, String oldPassword, String newPassword) {
        user = super.queryForOne(user);
        if (!user.getPassword().equals(SecurityUtil.encryptUserPassword(oldPassword, user.getUsername()))) {
            return "wrong old password!";
        }
        user.setPassword(SecurityUtil.encryptUserPassword(newPassword, user.getUsername()));
        super.updateNotNullable(user);

        return "reset success";

    }

    @Override
    public String resetEmail(User user, String oldEmail, String newEmail) {
        if (Objects.nonNull(this.getUserByEmail(newEmail))) {
            return "newEmailExist";
        } else {
            user.setEmail(newEmail);
            super.updateNotNullable(user);
            return "successReset";
        }

    }

    @Override
    public User getUser(Long id) {
        return super.queryForOne(new User().setId(id));
    }

    @Override
    public void updateUser(User user) throws Exception {
        super.updateNotNullable(user);
    }

    @Override
    public boolean ifUserIsAdmin(HttpSession session) {
        User userTemp = (User) session.getAttribute("user");
        if (userTemp.getIsAdmin().equals((byte) 1)) {
            return true;
        }
        return false;
    }

}
