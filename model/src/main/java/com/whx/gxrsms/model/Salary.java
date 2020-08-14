package com.whx.gxrsms.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 *@author create by Mybatis-generator
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class Salary implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 员工编号
     */
    @Column(name = "emp_no")
    private String empNo;

    /**
     * 基本薪资
     */
    private BigDecimal jiben;

    /**
     * 津贴
     */
    private BigDecimal jintie;

    /**
     * 课时费
     */
    private BigDecimal keshi;

    /**
     * 缴纳社保
     */
    private BigDecimal shebao;

    /**
     * 应发薪资
     */
    private BigDecimal salary;

    /**
     * 本月发薪时间
     */
    @Column(name = "extend_time")
    private String extendTime;

    /**
     * 发放状态
     */
    private String finished;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    private static final long serialVersionUID = 1L;
}