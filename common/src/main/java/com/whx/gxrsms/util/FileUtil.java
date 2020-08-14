package com.whx.gxrsms.util;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * @author ZhaoShuai
 * @company lihfinance.com
 * @date Create in 2020/2/23
 **/
public class FileUtil {

    private static Logger logger = LoggerFactory.getLogger(FileUtil.class);


    /**
     * @param filePath 文件存放位置
     * @param fileName 原始文件名
     * @return java.lang.String
     * @company lihfinance.com
     * @author create by ZhaoShuai in 2020/3/3
     * 获取文件名
     **/
    public static String getFileName(String filePath, String fileName) {
        return filePath + "/" + new SimpleDateFormat("yyyy/MM/dd").format(new Date()) +
                "/" + UUID.randomUUID().toString().replace("-", "") + fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * @param file
     * @return java.lang.String
     * @company lihfinance.com
     * @author create by ZhaoShuai in 2020/3/3
     * 上传文件
     **/
    public static String upload(MultipartFile file, String filePath) throws IOException {

        File saveFile = new File(filePath);
        if (saveFile.exists()) {
            boolean delete = saveFile.delete();
            if (!delete) {
                throw new RuntimeException("文件已存在，删除失败");
            }
        }
        if (!saveFile.getParentFile().exists()) {
            if (!saveFile.mkdirs()) {
                logger.error("文件名为:[{}]的文件夹创建失败", filePath);
            }
        }

        file.transferTo(saveFile);
        return filePath;
    }

    public static void removeFile(String fileName) {
        File file = new File(fileName);
        if (file.exists()) {
            Assert.isTrue(file.delete(), "文件" + fileName + "删除失败");;
        }
    }
}
