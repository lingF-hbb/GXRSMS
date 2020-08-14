package com.whx.gxrsms.model;

import java.io.Serializable;
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
public class Employee implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 员工编号
     */
    @Column(name = "emp_no")
    private String empNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * 性别
     */
    private String gender;

    /**
     * 居住地址
     */
    private String address;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 所属部门
     */
    @Column(name = "dept_no")
    private String deptNo;

    /**
     * 工作状态: 0-离职 1-在职
     */
    @Column(name = "work_status")
    private String workStatus;

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