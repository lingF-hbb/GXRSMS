package com.whx.gxrsms.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author ZhaoShuai
 * @company lihfinance.com
 * @date Create in 2020/3/3
 **/
@Data
@Accessors(chain = true)
public class MenuTree implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String title;

    private String icon;

    private String href;

    private List<MenuTree> list;
}
