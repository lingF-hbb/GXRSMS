package com.whx.gxrsms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @Company lihfinance.com
 * @Author ZhaoShuai
 * @Date Create in 2020/2/20
 **/
@SpringBootApplication
@MapperScan(basePackages = "com.whx.gxrsms.mapper")
public class ApplicationRun {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRun.class);
    }

}
