package com.whx.gxrsms.mapper;

import com.whx.gxrsms.model.Dept;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *@author Created by Mybatis Generator
 */
public interface DeptMapper extends Mapper<Dept> {

    /**
     * 模糊查询查部门信息
     * @param message
     * @return
     */
    List<Dept> queryDepInfo(String message);
}