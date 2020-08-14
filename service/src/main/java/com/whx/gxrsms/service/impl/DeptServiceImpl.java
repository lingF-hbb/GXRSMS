package com.whx.gxrsms.service.impl;

import com.whx.gxrsms.base.AbstractService;
import com.whx.gxrsms.mapper.DeptMapper;
import com.whx.gxrsms.model.Dept;
import com.whx.gxrsms.service.DeptService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeptServiceImpl extends AbstractService<Dept> implements DeptService {
    @Resource
    private DeptMapper deptMapper;

    @Override
    protected Mapper<Dept> getMapper() {
        return deptMapper;
    }

    @Override
    public List<Dept> getAll() {
        return deptMapper.selectAll();
    }

    @Override
    public String checkDepName(String depName) {
        if (deptMapper.selectCount(new Dept().setDeptName(depName)) > 0) {
            return "depExist";
        }
        return "depNotExist";
    }

    @Override
    public List<Dept> queryDepInfo(String message) {
        return deptMapper.queryDepInfo(message);
    }

    @Override
    public void deleteManyDeps(List<Long> depsId) throws Exception {
        super.delete(new Dept(), depsId);
    }

    @Override
    public String insertDep(Dept department) {
        deptMapper.insertSelective(department);
        return "insertSuccess";
    }

    @Override
    public String deleteOneDep(Long depId) {
        deptMapper.deleteByPrimaryKey(depId);
        return "deleteSuccess";
    }

    @Override
    public String updateDep(Dept department) {
        super.updateNotNullable(department);
        return "updateSuccess";
    }
}
