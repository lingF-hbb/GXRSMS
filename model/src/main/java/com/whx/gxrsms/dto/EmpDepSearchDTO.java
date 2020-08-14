package com.whx.gxrsms.dto;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 */
@Data
@ToString
public class EmpDepSearchDTO implements Serializable {

        private static final long serialVersionUID = 1L;

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
        @Column(name = "name")
        private String name;

        /**
         * 居住地址
         */
        @Column(name = "address")
        private String address;

        /**
         * 联系方式
         */
        private String phone;

        private String gender;

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

        @Column(name="dept_name")
        private String deptName;

}
