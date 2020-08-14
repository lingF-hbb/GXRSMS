package com.whx.gxrsms.web;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.whx.gxrsms.base.BaseController;
import com.whx.gxrsms.bean.Page;
import com.whx.gxrsms.dto.SanctionDTO;
import com.whx.gxrsms.model.Sanction;
import com.whx.gxrsms.service.SanctionsService;
import com.whx.gxrsms.util.Layui;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ZhaoShuai
 * @company lihfinance.com
 * 奖惩管理
 * @date Create in 2020/2/22
 **/
@Controller
@RequestMapping("/good")
public class SanctionsController extends BaseController {

    private final SanctionsService sanctionService;

    public SanctionsController(SanctionsService sanctionsService) {
        this.sanctionService = sanctionsService;
    }

    /**
     *
     * 功能描述: 查询所有工资
     *
     * @param: myPage
     * @return: ResponseEntity<Layui>分页结果
     */
    @RequestMapping("queryAllgood.html")
    @ResponseBody
    public ResponseEntity<Layui> showAllgood(){
        Page<Sanction> page = getPage(Sanction.class);
        com.github.pagehelper.Page<Object> pages = PageHelper.startPage(page.getPageNum(), page.getPageSize());
        sanctionService.queryAllSanction(null);

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
    @RequestMapping("toEditGood.html")
    public String toAddgood(Model model, Sanction employee){
        model.addAttribute("empInfo",employee);
        return "modify/goodForm";

    }

    /**
     *
     * 功能描述: 查询奖惩
     *
     * @param: myPage,employee
     * @return: ResponseEntity<Layui>分页结果
     */
    @RequestMapping("querygoodInfo.html")
    @ResponseBody
    public ResponseEntity<Layui> querygood(String msg){
        Page<Sanction> page = getPage(Sanction.class);
        com.github.pagehelper.Page<Object> pages = PageHelper.startPage(page.getPageNum(), page.getPageSize());
        sanctionService.queryAllSanction(msg);

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
    @RequestMapping("updategood.html")
    @ResponseBody
    public Map<String,Object> updategood(Sanction sanction){
        Map<String,Object> map = new HashMap<>();
        sanctionService.updateNotNullable(sanction);
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
    @RequestMapping("deleteOnegood.html")
    @ResponseBody
    public Map<String,Object> deleteOnegood(@RequestParam("id") Long id){
        Map<String,Object> map = new HashMap<>(4);
        try {
            sanctionService.delete(id);
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
    @RequestMapping("deleteManygoods.html")
    @ResponseBody
    public Map<String ,Object> deleteManygood(@RequestParam("goods") String sanctions){
        Map<String,Object> map = new HashMap<>(4);
        List<SanctionDTO> salado = JSON.parseArray(sanctions, SanctionDTO.class);
        List<Long> ids = salado.parallelStream().map(Sanction::getId).collect(Collectors.toList());

        try {
            sanctionService.delete(new Sanction(), ids);
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
    @RequestMapping("insertgood.html")
    @ResponseBody
    public Map<String,Object> insertgood(Sanction sanction){
        HashMap<String,Object> map = new HashMap<>();
        sanctionService.insert(sanction);
        map.put("data", "insertSuccess");
        return map;
    }
}
