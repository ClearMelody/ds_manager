package com.wyk.dsmanager.service.impl;

import com.wyk.dsmanager.entity.DsDatabase;
import com.wyk.dsmanager.entity.vo.DsDatabaseVo;
import com.wyk.dsmanager.repository.DatabaseRep;
import com.wyk.dsmanager.service.IDatabaseService;
import com.wyk.dsmanager.utils.AbstractMyBeanUtils;
import com.wyk.dsmanager.utils.PageResult;
import com.wyk.dsmanager.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/2
 * \*
 * \* Description
 *
 * @author wyk
 */
@Service("databaseService")
public class DataBaseServiceImpl implements IDatabaseService {
    @Resource(name = "databaseRep")
    DatabaseRep databaseRep;

    @Override
    public Result save(DsDatabaseVo vo) {
        String id = vo.getId();
        DsDatabase entity = new DsDatabase();
        if(StringUtils.isNotBlank(id)){
            entity = databaseRep.getOne(id);
        }else{
            entity.setCreateTime(LocalDateTime.now());
        }
        entity.setUpdateTime(LocalDateTime.now());
        AbstractMyBeanUtils.copyProperties(vo, entity);
        databaseRep.save(entity);
        return Result.ok();
    }

    @Override
    public Result getOne(String id) {
        DsDatabase entity = databaseRep.getOne(id);
        DsDatabaseVo vo = DsDatabaseVo.convert(entity);
        return Result.ok(vo);
    }

    @Override
    public Result listPage(Map<String, Object> paramMap, Pageable pageable) {
        Page<DsDatabaseVo> page = databaseRep.findEntitiesForListPage((String) paramMap.get("dbName"), (String) paramMap.get("dbUrl"), (String) paramMap.get("dbType"), pageable);
        return Result.ok(PageResult.setPageResult(pageable, page.getTotalElements(), page.getContent()));
    }

    @Override
    public Result listAll() {
        List<DsDatabaseVo> list = databaseRep.listAll();
        return Result.ok(list);
    }

    @Override
    public void delete(String id) {
        databaseRep.deleteById(id);
    }
}
