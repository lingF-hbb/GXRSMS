package com.whx.gxrsms.web;

import com.whx.gxrsms.bean.Page;
import com.whx.gxrsms.base.BaseController;
import com.whx.gxrsms.bean.Result;
import com.whx.gxrsms.model.Files;
import com.whx.gxrsms.service.FileService;
import com.whx.gxrsms.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

/**
 * @author ZhaoShuai
 * @company lihfinance.com
 * 档案管理
 * @date Create in 2020/2/22
 **/
@RestController
@RequestMapping("/fileManage")
public class FileManageController extends BaseController {

    @Autowired
    private FileService fileService;

    @RequestMapping("/queryFileList")
    /**
     * @company lihfinance.com
     * @author create by ZhaoShuai in 2020/3/4
     *  查询所有
     * @param [files]
     * @return com.whx.gxrsms.bean.Page<com.whx.gxrsms.model.Files>
     **/
    public Page<Files> getFileList(Files files) {
        Page<Files> page = getPage(Files.class);
        return  fileService.queryForPageList(files, page);
    }

    @RequestMapping("/edit/{id}")
    public Result edit(@PathVariable("id") Long id) {
        Result result = Result.success();
        Files files = fileService.queryForOne(new Files().setId(id));
        if (Objects.isNull(files)) {
            result.setFail("找不到该数据");
        }
        result.setData(files);
        return result;
    }

    @RequestMapping("/save")
    /**
     * @company lihfinance.com
     * @author create by ZhaoShuai in 2020/3/4
     *  修改/新增
     * @param [file, files]
     * @return com.whx.gxrsms.bean.Result
     **/
    public Result upload(MultipartFile file, Files files) {

        Result result = Result.success();
        try {
            fileService.save(file, files);
        } catch (Exception e) {
            result.setFail(e.getMessage());
        }
        return result;
    }

    @RequestMapping("/delete/{id}")
    /**
     * @company lihfinance.com
     * @author create by ZhaoShuai in 2020/3/4
     *  删除
     * @param [id]
     * @return com.whx.gxrsms.bean.Result
     **/
    public Result delete(@PathVariable("id") Long id) {

        Result result = Result.success();
        try {
            fileService.deleteFile(id);
        } catch (Exception e) {
            result.setFail(e.getMessage());
        }
        return result;
    }
}
