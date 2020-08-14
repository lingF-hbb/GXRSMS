package com.whx.gxrsms.mapper;

import com.whx.gxrsms.dto.EmpSalaryDTO;
import com.whx.gxrsms.model.Salary;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *@author Created by Mybatis Generator
 */
public interface SalaryMapper extends Mapper<Salary> {
    List<EmpSalaryDTO> queryAll(String msg);
}