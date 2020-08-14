package com.whx.gxrsms.web;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whx.gxrsms.base.BaseController;
import com.whx.gxrsms.bean.Page;
import com.whx.gxrsms.bean.Result;
import com.whx.gxrsms.model.Dept;
import com.whx.gxrsms.service.DeptService;
import com.whx.gxrsms.util.Layui;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @description : 控制器-部门管理页面
 */
@Controller
@RequestMapping("/department")
public class DepartmentController extends BaseController {

    private final DeptService deptService;

    public DepartmentController(DeptService deptService) {
        this.deptService = deptService;
    }

    /**
     * 功能描述: 查询所有的部门
     *
     * @return: ResponseEntity<Layui>分页信息
     */
    @RequestMapping("queryAllDep.html")
    @ResponseBody
    public ResponseEntity<Layui> queryAllDep() {
        Page<Dept> page = getPage(Dept.class);
        Page<Dept> deptPage = deptService.queryForPageList(new Dept(), page);

        Layui layui = Layui.data(deptPage.getTotal(), deptPage.getRows());
        return ResponseEntity.ok(layui);
    }

    /**
     * 功能描述: 检查部门是否存在
     *
     * @param: dept
     * @return: Map<String, Object>
     */
    @RequestMapping("checkDepName.html")
    @ResponseBody
    public Map<String, Object> checkDepName(Dept dept) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", deptService.checkDepName(dept.getDeptName()));
        return map;
    }

    @RequestMapping("/getDepName.html")
    @ResponseBody
    public Result getDeptAll() {
        Result success = Result.success();
        success.setData(deptService.getAll());
        return success;
    }

    /**
     * 功能描述: 查询部门信息
     *
     * @param: myPage, Msg
     * @return: ResponseEntity<Layui>分页信息
     */
    @RequestMapping("queryDepInfo.html")
    @ResponseBody
    public ResponseEntity<Layui> queryDep(@RequestParam("Msg") String Msg) {
        Page<Dept> page = getPage(Dept.class);
        com.github.pagehelper.Page<Object> pages = PageHelper.startPage(page.getPageNum(), page.getPageSize());
        deptService.queryDepInfo(Msg);
        Layui layui = Layui.data(pages.getTotal(), pages.getResult());
        return ResponseEntity.ok(layui);
    }

    /**
     * 功能描述: 删除指定的部门
     *
     * @param: dept
     * @return: Map<String, Object>
     */
    @RequestMapping("deleteOneDep.html")
    @ResponseBody
    public Map<String, Object> deleteOneDep(Dept dept) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", deptService.deleteOneDep(dept.getId()));
        return map;
    }

    /**
     * 功能描述: 批量删除部门信息
     *
     * @param: deps
     * @return: Map<String, Object>
     */
    @RequestMapping("deleteManyDeps.html")
    @ResponseBody
    public Result deleteManyDeps(@RequestParam("deps") String deps) {
        Result result = Result.success();

        try {
            List<Dept> deptList = JSON.parseArray(deps, Dept.class);
            List<Long> depsId = new ArrayList<>();
            for (Dept dept : deptList) {
                depsId.add(dept.getId());
            }
            deptService.deleteManyDeps(depsId);
        } catch (Exception e) {
            result.setFail();
        }
        return result;
    }

    /**
     * 功能描述: 跳转编辑部门的页面
     *
     * @param: dept
     * @return: String
     */
    @RequestMapping("toEditDep.html")
    public String toEditDep(Model model, Dept dept) {
        model.addAttribute("depInfo", dept);
        return "modify/departmentForm";
    }

    /**
     * 功能描述: 添加部门
     *
     * @param: dept
     * @return: Map<String, Object>
     */
    @RequestMapping("insertDep.html")
    @ResponseBody
    public Map<String, Object> insertDep(Dept dept) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", deptService.insertDep(dept));
        return map;
    }


    /**
     * 功能描述: 更新部门信息
     *
     * @param: dept
     * @return: Map<String, Object>
     */
    @RequestMapping("updateDep.html")
    @ResponseBody
    public Map<String, Object> updateDep(Dept dept) {
        Map<String, Object> map = new HashMap<>();
        map.put("data", deptService.updateDep(dept));
        return map;
    }
}
