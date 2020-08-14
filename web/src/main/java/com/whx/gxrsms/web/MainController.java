package com.whx.gxrsms.web;

import com.whx.gxrsms.base.BaseController;
import com.whx.gxrsms.bean.Page;
import com.whx.gxrsms.bean.Result;
import com.whx.gxrsms.model.Dept;
import com.whx.gxrsms.model.User;
import com.whx.gxrsms.service.DeptService;
import com.whx.gxrsms.service.EmployeeService;
import com.whx.gxrsms.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;


/**
 * @project : HRM
 * @description : 控制器-首页管理
 */
@Controller
@RequestMapping("/main")
public class MainController extends BaseController {
    private final UserService userService;
    private final DeptService deptService;
    private final EmployeeService employeeService;

    public MainController(UserService userService, DeptService deptService, EmployeeService employeeService) {
        this.userService = userService;
        this.deptService = deptService;
        this.employeeService = employeeService;
    }

    /**
     * @param user
     * @param httpSession
     * @return
     * @decription 更改个人信息
     */
    @RequestMapping("updateUser.html")
    @ResponseBody
    public Result updateUser(@RequestBody User user, HttpSession httpSession) {
        Result result = Result.success();

        try {
            userService.updateUser(user);
            httpSession.setAttribute("user", user.getId());
        } catch (Exception e) {
            result.setFail(e.getMessage());
        }

        return result;
    }

    @RequestMapping("toHome.html")
    public String toHome () {
        User user = (User) super.getHttpServletRequest().getSession().getAttribute("user");
        if (Byte.valueOf("1").equals(user.getIsAdmin())) {
            return "/home/home_admin";
        }
        return "home/home";
    }

    /**
     * @return
     * @decription 跳转薪资列表页面
     */
    @RequestMapping("toMoneyList.html")
    public String toMoneyList() {
        return "query/MoneyList";
    }
    /**
     * @return
     * @decription 跳转奖惩列表页面
     */
    @RequestMapping("toGoodList.html")
    public String toGoodList() {
        return "query/GoodList";
    }
    /**
     * @return
     * @decription 跳转员工列表页面
     */
    @RequestMapping("toEmployeeList.html")
    public String toEmployeeList() {
        return "query/EmployeeList";
    }

    /**
     * @return
     * @decription 跳转到添加员工页面
     */
    @RequestMapping("toModifyEmp.html")
    public String toAddEmployee() {
        return "modify/modifyEmployee";
    }
    /**
     * @return
     * @decription 跳转到修改奖惩页面
     */
    @RequestMapping("toModifyGood.html")
    public String toModifyGood() {
        return "modify/modifyGood";
    }
    /**
     * @return
     * @decription 跳转到修改薪资页面
     */
    @RequestMapping("toModifyMoney.html")
    public String toModifyMoney() {
        return "modify/modifyMoney";
    }

    /**
     * @param model
     * @return
     * @decription 查询所有部门信息并跳转到部门信息页面(分页查询)
     */
    @RequestMapping("toDepartmentList.html")
    public String toDepartmentList(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
        //pn为当前页码,每页的大小为5
        Page<Dept> page = getPage(Dept.class);
        page.setPageNum(pn);
        page.setPageSize(5);

        Page<Dept> deptPage = deptService.queryForPageList(new Dept(), page);
        model.addAttribute("pageInfo", deptPage);
        return "query/DepartmentList";
    }

    /**
     * @return
     * @decription 跳转到添加部门页面
     */
    @RequestMapping("toModifyDep.html")
    public String toAddDepartment() {
        return "modify/modifyDepartment";
    }

    /**
     * 功能描述: 跳转到错误页面(权限不足)
     *
     * @param: null
     * @return: String
     */
    @RequestMapping("toError.html")
    public String toError() {
        return "error/error";
    }
}
