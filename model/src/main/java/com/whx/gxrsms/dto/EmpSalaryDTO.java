package com.whx.gxrsms.dto;

import com.whx.gxrsms.model.Salary;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ZhaoShuai
 * @date Create in 2020/5/4
 **/
@Data
public class EmpSalaryDTO extends Salary implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

}
