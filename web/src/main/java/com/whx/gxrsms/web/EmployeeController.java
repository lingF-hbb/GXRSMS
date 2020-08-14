package com.whx.gxrsms.web;


import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.whx.gxrsms.base.BaseController;
import com.whx.gxrsms.bean.Page;
import com.whx.gxrsms.dto.EmpDepSearchDTO;
import com.whx.gxrsms.model.Employee;
import com.whx.gxrsms.service.EmployeeService;
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

@Controller
@RequestMapping("employee")
public class EmployeeController extends BaseController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     *
     * 功能描述: 查询所有员工
     *
     * @param: myPage
     * @return: ResponseEntity<Layui>分页结果
     */
    @RequestMapping("queryAllEmp.html")
    @ResponseBody
    public ResponseEntity<Layui> showAllEmp(){
        Page<Employee> page = getPage(Employee.class);
        com.github.pagehelper.Page<Object> pages = PageHelper.startPage(page.getPageNum(), page.getPageSize());
        employeeService.queryAllEmpInfo();

        Layui layui = Layui.data(pages.getTotal(),pages.getResult());
        return ResponseEntity.ok(layui);
    }


    /**
     *
     * 功能描述: 跳转编辑员工页面
     *
     * @param: model,empDepSearchDTO
     * @return: String
     */
    @RequestMapping("toEditEmp.html")
    public String toAddEmp(Model model, Employee employee){
        model.addAttribute("empInfo",employee);
        return "modify/employeeForm";
    }
    /**
     *
     * 功能描述: 检查员工姓名是否重复
     *
     * @param: employee
     * @return: Map<String,Object>
     */
    @RequestMapping("checkEmpName.html")
    @ResponseBody
    public Map<String,Object> checkEmpName(Employee employee){
        Map<String,Object> map = new HashMap<>();
        map.put("data",employeeService.checkEmpName(employee));
        return map;
    }


    /**
     *
     * 功能描述: 查询员工
     *
     * @param: myPage,employee
     * @return: ResponseEntity<Layui>分页结果
     */
    @RequestMapping("queryEmpInfo.html")
    @ResponseBody
    public ResponseEntity<Layui> queryEmp(Employee employee){
        Page<Employee> page = getPage(Employee.class);
        com.github.pagehelper.Page<Object> pages = PageHelper.startPage(page.getPageNum(), page.getPageSize());
        employeeService.queryEmpInfo(employee);

        Layui layui = Layui.data(pages.getTotal(),pages.getResult());
        return ResponseEntity.ok(layui);
    }

    /**
     *
     * 功能描述: 更新员工信息
     *
     * @param: empDepSearchDTO
     * @return: Map<String,Object>
     */
    @RequestMapping("updateEmp.html")
    @ResponseBody
    public Map<String,Object> updateEmp(Employee employee){
        Map<String,Object> map = new HashMap<>();
        map.put("data",employeeService.updateEmp(employee));
        return map;
    }

    /**
     *
     * 功能描述: 删除某个指定的员工
     *
     * @param: empId
     * @return: Map<String,Object>
     */
    @RequestMapping("deleteOneEmp.html")
    @ResponseBody
    public Map<String,Object> deleteOneEmp(@RequestParam("empId") Long empId){
        Map<String,Object> map = new HashMap<>();
        employeeService.deleteOneEmp(empId);
        map.put("data","successDelete");
        return map;
    }

    /**
     *
     * 功能描述: 批量删除员工信息
     *
     * @param: emps
     * @return: Map<String,Object>
     */
    @RequestMapping("deleteManyEmps.html")
    @ResponseBody
    public Map<String ,Object> deleteManyEmps(@RequestParam("emps") String emps){
        Map<String,Object> map = new HashMap<>();
        List<EmpDepSearchDTO> empDepSearchDTOList = JSON.parseArray(emps,EmpDepSearchDTO.class);
        List<Long> empsId = new ArrayList<>();
        for(EmpDepSearchDTO empDepSearchDTO: empDepSearchDTOList){
            empsId.add(empDepSearchDTO.getId());
        }
        try {
            employeeService.deleteManyEmps(empsId);
            map.put("data","deleteSuccess");
        } catch (Exception e) {
            map.put("data", "fail delete");
        }

        return map;
    }


    /**
     *
     * 功能描述: 添加新员工
     *
     * @param: empDepSearchDTO
     * @return: Map<String,Object>
     */
    @RequestMapping("insertEmp.html")
    @ResponseBody
    public Map<String,Object> insertEmp(Employee employee){
        HashMap<String,Object> map = new HashMap<>();
        map.put("data",employeeService.insertEmp(employee));
        return map;
    }


}
