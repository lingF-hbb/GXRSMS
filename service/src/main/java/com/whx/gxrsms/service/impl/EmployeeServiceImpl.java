package com.whx.gxrsms.service.impl;

import com.whx.gxrsms.base.AbstractService;
import com.whx.gxrsms.dto.EmpDepSearchDTO;
import com.whx.gxrsms.mapper.DeptMapper;
import com.whx.gxrsms.mapper.EmployeeMapper;
import com.whx.gxrsms.model.Employee;
import com.whx.gxrsms.service.EmployeeService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class EmployeeServiceImpl extends AbstractService<Employee> implements EmployeeService {

    @Resource
    private EmployeeMapper employeeMapper;
    @Resource
    private DeptMapper deptMapper;

    @Override
    protected Mapper<Employee> getMapper() {
        return employeeMapper;
    }

    @Override
    public List<EmpDepSearchDTO> queryAllEmpInfo() {
        return employeeMapper.queryAll();
    }

    @Override
    public String checkEmpName(Employee employee) {
        if (employeeMapper.selectCount(employee) > 0) {
            return "empnameExist";
        }
        return "empnameNotExist";

    }

    @Override
    public List<EmpDepSearchDTO> queryEmpInfo(Employee employee) {
        return employeeMapper.queryEmpInfo(employee);
    }

    @Override
    public void deleteOneEmp(Long empId) {
        employeeMapper.deleteByPrimaryKey(empId);
    }

    @Override
    public void deleteManyEmps(List<Long> empsId) throws Exception {
        super.delete(new Employee(),empsId);
    }

    @Override
    public String insertEmp(Employee employee) {
        super.insert(employee);
        return "insertSuccess";
    }

    @Override
    public String updateEmp(Employee employee) {
        super.updateNotNullable(employee);
        return "updateSuccess";
    }


}
