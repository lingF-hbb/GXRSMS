package com.whx.gxrsms.mapper;

import com.whx.gxrsms.model.User;
import tk.mybatis.mapper.common.Mapper;

/**
 *@author Created by Mybatis Generator
 */
public interface UserMapper extends Mapper<User> {

    int resetPwdByEmail(User user);
}