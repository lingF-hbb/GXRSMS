package com.whx.gxrsms.service.impl;

import com.whx.gxrsms.base.AbstractService;
import com.whx.gxrsms.dto.SanctionDTO;
import com.whx.gxrsms.mapper.SanctionMapper;
import com.whx.gxrsms.model.Sanction;
import com.whx.gxrsms.service.SanctionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ZhaoShuai
 * @date Create in 2020/5/4
 **/
@Service
@Slf4j
public class SanctionServiceImpl extends AbstractService<Sanction> implements SanctionsService {

    @Resource
    private SanctionMapper sanctionMapper;
    @Override
    protected Mapper<Sanction> getMapper() {
        return sanctionMapper;
    }

    @Override
    public List<SanctionDTO> queryAllSanction(String msg) {
        return sanctionMapper.queryAllSanction(msg);
    }
}
