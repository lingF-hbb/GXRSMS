package com.whx.gxrsms.service;

import com.whx.gxrsms.BaseService;
import com.whx.gxrsms.model.Dept;

import java.util.List;

/**
 * @author 赵帅
 * @company lihfinance.com
 * @date Create in 2020/3/2
 **/
public interface DeptService extends BaseService<Dept> {

    //查询所有
    List<Dept> getAll();

    //检查部门名是否存在
    String checkDepName(String depName);

    //查询部门
    List<Dept> queryDepInfo(String msg);

    //
    void deleteManyDeps(List<Long> depsId) throws Exception;

    String insertDep(Dept department);

    String deleteOneDep(Long depId);

    String updateDep(Dept department);
}
