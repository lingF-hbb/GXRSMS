package com.whx.gxrsms.service;

import com.whx.gxrsms.BaseService;
import com.whx.gxrsms.dto.EmpDepSearchDTO;
import com.whx.gxrsms.model.Employee;

import java.util.List;

/**
 * @author 赵帅
 * @company lihfinance.com
 * @date Create in 2020/3/2
 **/
public interface EmployeeService extends BaseService<Employee> {
    /**
     *
     * 功能描述: 查询所有职员的信息分页结果
     *
     * @param: myPage,employee
     * @return: PageInfo<Employee>
     */
    List<EmpDepSearchDTO> queryAllEmpInfo();

    String checkEmpName(Employee employee);

    List<EmpDepSearchDTO> queryEmpInfo(Employee employee);

    void deleteOneEmp(Long empId);

    void deleteManyEmps(List<Long> empsId) throws Exception;

    String insertEmp(Employee employee);

    String updateEmp(Employee employee);
}
