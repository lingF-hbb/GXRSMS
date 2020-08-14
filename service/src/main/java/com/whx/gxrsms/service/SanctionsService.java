package com.whx.gxrsms.service;

import com.whx.gxrsms.BaseService;
import com.whx.gxrsms.dto.SanctionDTO;
import com.whx.gxrsms.model.Sanction;

import java.util.List;

/**
 * @author ZhaoShuai
 * @date Create in 2020/5/4
 **/
public interface SanctionsService extends BaseService<Sanction> {

    List<SanctionDTO> queryAllSanction(String msg);
}
