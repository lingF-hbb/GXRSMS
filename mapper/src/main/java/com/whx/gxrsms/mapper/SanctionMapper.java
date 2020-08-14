package com.whx.gxrsms.mapper;

import com.whx.gxrsms.dto.SanctionDTO;
import com.whx.gxrsms.model.Sanction;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 *@author Created by Mybatis Generator
 */
public interface SanctionMapper extends Mapper<Sanction> {
    List<SanctionDTO> queryAllSanction(String msg);
}