package com.whx.gxrsms.service.impl;

import com.alibaba.fastjson.JSON;
import com.whx.gxrsms.base.AbstractService;
import com.whx.gxrsms.mapper.FilesMapper;
import com.whx.gxrsms.model.Files;
import com.whx.gxrsms.service.FileService;
import com.whx.gxrsms.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author ZhaoShuai
 * @company lihfinance.com
 * @date Create in 2020/2/23
 **/
@Service
public class FilesServiceImpl extends AbstractService<Files> implements FileService {

    private Logger logger = LoggerFactory.getLogger(FilesServiceImpl.class);

    @Resource
    private FilesMapper fileMapper;

    @Value("${file-path}")
    private String filePath;

    @Override
    protected Mapper<Files> getMapper() {
        return fileMapper;
    }

    @Override
    public void save(MultipartFile file, Files files) throws Exception {
        Long id = files.getId();

        // 判断是新增还是修改操作
        if (Objects.isNull(id)) {
            logger.info("当前执行新增文件操作,操作对象为：[{}]", JSON.toJSONString(files));

            String realFilePath = FileUtil.getFileName(filePath, Objects.requireNonNull(file.getOriginalFilename()));
            files.setFilePath(realFilePath);
            fileMapper.insert(files);
        } else {
            logger.info("当前执行修改文件操作,要操作的对象为：[{}]", JSON.toJSONString(files));
            if (!file.isEmpty()) {
                logger.info("需要修改文件内容");
                Files oldRecord = fileMapper.selectOne(new Files().setId(id));
                if (Objects.isNull(oldRecord)) {
                    throw new Exception("找不到要修改的记录");
                }

                String originalFilename = file.getOriginalFilename();
                String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
                if (oldRecord.getFilePath().endsWith(suffix)) {
                    FileUtil.upload(file, oldRecord.getFilePath());
                } else {
                    String realFilePath = FileUtil.getFileName(filePath,originalFilename);
                    FileUtil.removeFile(oldRecord.getFilePath());
                    FileUtil.upload(file, realFilePath);
                    files.setFilePath(realFilePath);
                }

            }
            fileMapper.updateByPrimaryKeySelective(files);

        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteFile(Long id) throws Exception {

        Files files = fileMapper.selectOne(new Files().setId(id));
        if (Objects.isNull(files)) {
            throw new Exception("找不到该记录");
        }

        FileUtil.removeFile(files.getFilePath());
//        if (!remove) {
//            logger.error("文件[{}]删除失败,请检查", files.getFilePath());
//            throw new Exception("删除失败");
//        }
        delete(files.getId());
    }
}
