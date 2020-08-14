package com.whx.gxrsms.dto;

import com.whx.gxrsms.model.Sanction;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * @author ZhaoShuai
 * @date Create in 2020/5/4
 **/
@Data
public class SanctionDTO extends Sanction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "dept_name")
    private String deptName;

    private String name;
}
