package com.whx.gxrsms.service;

import com.whx.gxrsms.BaseService;
import com.whx.gxrsms.dto.EmpSalaryDTO;
import com.whx.gxrsms.model.Salary;

import java.util.List;

/**
 * @author 赵帅
 * @company lihfinance.com
 * @date Create in 2020/3/2
 **/
public interface SalaryService extends BaseService<Salary> {

    /**
     * 查询所有工资
     * @param msg
     * @return 所有工资数据
     */
    List<EmpSalaryDTO> queryAllSalary(String msg);
}
