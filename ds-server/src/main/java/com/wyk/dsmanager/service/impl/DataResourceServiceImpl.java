package com.wyk.dsmanager.service.impl;

import com.wyk.dsmanager.entity.DsDataResource;
import com.wyk.dsmanager.entity.vo.DsDataResourceVo;
import com.wyk.dsmanager.repository.DataResourceRep;
import com.wyk.dsmanager.service.IDataResourceService;
import com.wyk.dsmanager.utils.AbstractMyBeanUtils;
import com.wyk.dsmanager.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/01/02
 * \*
 * \* Description
 *
 * @author wyk
 */
@Service("dataResourceService")
public class DataResourceServiceImpl implements IDataResourceService {
    @Resource(name = "dataResourceRep")
    DataResourceRep dataResourceRep;

    @Override
    public Result save(DsDataResourceVo vo) {
        DsDataResource resource = dataResourceRep.findDsDataResourceByDsName(vo.getDsName());
        if(resource != null){
            //名称存在
            return Result.ok(-1);
        }
        String id = vo.getId();
        DsDataResource entity = new DsDataResource();
        if(StringUtils.isNotBlank(id)){
            entity = dataResourceRep.getOne(id);
        }else{
            entity.setCreateTime(LocalDateTime.now());
        }
        AbstractMyBeanUtils.copyProperties(vo, entity);
        dataResourceRep.save(entity);
        return Result.ok();
    }

    @Override
    public Result listAll() {
        return Result.ok(dataResourceRep.listAll());
    }

    @Override
    public Result delete(String id) {
        dataResourceRep.deleteById(id);
        return Result.ok();
    }
}
