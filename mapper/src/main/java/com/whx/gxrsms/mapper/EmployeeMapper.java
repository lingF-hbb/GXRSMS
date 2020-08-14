package com.whx.gxrsms.mapper;

import com.whx.gxrsms.dto.EmpDepSearchDTO;
import com.whx.gxrsms.model.Employee;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *@author Created by Mybatis Generator
 */
public interface EmployeeMapper extends Mapper<Employee> {

    List<EmpDepSearchDTO> queryAll();

    List<EmpDepSearchDTO> queryEmpInfo(Employee employee);
}