package com.whx.gxrsms.web;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.whx.gxrsms.base.BaseController;
import com.whx.gxrsms.bean.Page;
import com.whx.gxrsms.dto.EmpDepSearchDTO;
import com.whx.gxrsms.dto.EmpSalaryDTO;
import com.whx.gxrsms.model.Salary;
import com.whx.gxrsms.service.SalaryService;
import com.whx.gxrsms.util.Layui;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ZhaoShuai
 * @company lihfinance.com
 * 薪资管理
 * @date Create in 2020/2/22
 **/
@Slf4j
@Controller
@RequestMapping("/money")
public class SalaryController extends BaseController {

    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    /**
     *
     * 功能描述: 查询所有工资
     *
     * @param: myPage
     * @return: ResponseEntity<Layui>分页结果
     */
    @RequestMapping("queryAllMoney.html")
    @ResponseBody
    public ResponseEntity<Layui> showAllEmp(@RequestParam(value = "Msg",required = false) String msg){
        Page<Salary> page = getPage(Salary.class);
        com.github.pagehelper.Page<Object> pages = PageHelper.startPage(page.getPageNum(), page.getPageSize());
        salaryService.queryAllSalary(msg);

        Layui layui = Layui.data(pages.getTotal(),pages.getResult());
        return ResponseEntity.ok(layui);
    }


    /**
     *
     * 功能描述: 跳转编辑工资页面
     *
     * @param: model,empDepSearchDTO
     * @return: String
     */
    @RequestMapping("toEditMoney.html")
    public String toAddEmp(Model model, Salary salary){
        model.addAttribute("empInfo",salary);
        return "modify/moneyForm";
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
    public Map<String,Object> updateEmp(Salary salary){
        Map<String,Object> map = new HashMap<>();
        salaryService.updateNotNullable(salary);
        map.put("data","updateSuccess");
        return map;
    }

    /**
     *
     * 功能描述: 删除某个指定的员工
     *
     * @param: id
     * @return: Map<String,Object>
     */
    @RequestMapping("deleteOneMoney.html")
    @ResponseBody
    public Map<String,Object> deleteOneEmp(@RequestParam("id") Long id){
        Map<String,Object> map = new HashMap<>(4);
        try {
            salaryService.delete(id);
            map.put("data","successDelete");
        } catch (Exception e) {
            map.put("data", e.getMessage());
        }

        return map;
    }

    /**
     *
     * 功能描述: 批量删除员工信息
     *
     * @param: emps
     * @return: Map<String,Object>
     */
    @RequestMapping("deleteManyMoney.html")
    @ResponseBody
    public Map<String ,Object> deleteManyMoney(@RequestParam("salarys") String salarys){
        Map<String,Object> map = new HashMap<>(4);
        List<EmpSalaryDTO> salado = JSON.parseArray(salarys,EmpSalaryDTO.class);
        List<Long> ids = salado.parallelStream().map(Salary::getId).collect(Collectors.toList());

        try {
            salaryService.delete(new Salary(), ids);
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
    @RequestMapping("insertmoney.html")
    @ResponseBody
    public Map<String,Object> insertmoney(Salary salary){
        HashMap<String,Object> map = new HashMap<>();
        salaryService.insert(salary);
        map.put("data", "insertSuccess");
        return map;
    }


}
