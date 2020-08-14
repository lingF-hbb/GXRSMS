package com.whx.gxrsms.service;

import com.whx.gxrsms.BaseService;
import com.whx.gxrsms.model.Files;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author ZhaoShuai
 * @company lihfinance.com
 * @date Create in 2020/2/23
 **/
public interface FileService extends BaseService<Files> {

    /**
     * 保存文件
     * @param file
     * @param files
     * @throws Exception
     */
    void save(MultipartFile file, Files files) throws Exception;

    /**
     * 删除文件
     * @param id
     * @throws Exception
     */
    void deleteFile(Long id) throws Exception;


}
