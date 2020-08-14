package com.whx.gxrsms.service.impl;

import com.whx.gxrsms.base.AbstractService;
import com.whx.gxrsms.dto.EmpSalaryDTO;
import com.whx.gxrsms.mapper.SalaryMapper;
import com.whx.gxrsms.model.Salary;
import com.whx.gxrsms.service.SalaryService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ZhaoShuai
 * @company lihfinance.com
 * @date Create in 2020/3/2
 **/
@Service
public class SalaryServiceImpl extends AbstractService<Salary> implements SalaryService {

    @Resource
    private SalaryMapper salaryMapper;

    @Override
    protected Mapper<Salary> getMapper() {
        return salaryMapper;
    }

    @Override
    public List<EmpSalaryDTO> queryAllSalary(String msg) {
        return  salaryMapper.queryAll(msg);
    }
}
