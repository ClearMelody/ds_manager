package com.wyk.dsmanager.service.impl;

import com.google.common.collect.Maps;
import com.wyk.dsmanager.entity.DsDatabase;
import com.wyk.dsmanager.entity.DsDictionary;
import com.wyk.dsmanager.entity.DsDictionaryType;
import com.wyk.dsmanager.entity.vo.DsDatabaseVo;
import com.wyk.dsmanager.entity.vo.DsDictionaryVo;
import com.wyk.dsmanager.repository.DictionaryRep;
import com.wyk.dsmanager.repository.DictionaryTypeRep;
import com.wyk.dsmanager.service.IDictionaryService;
import com.wyk.dsmanager.utils.AbstractMyBeanUtils;
import com.wyk.dsmanager.utils.PageResult;
import com.wyk.dsmanager.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/13
 * \*
 * \* Description
 *
 * @author wyk
 */
@Service("dictionaryService")
public class DictionaryServiceImpl implements IDictionaryService {
    @Resource(name = "dictionaryRep")
    DictionaryRep dictionaryRep;
    @Resource(name = "dictionaryTypeRep")
    DictionaryTypeRep dictionaryTypeRep;

    @Override
    public Result save(DsDictionaryVo vo) {
        String id = vo.getId();
        DsDictionary entity = new DsDictionary();
        if(StringUtils.isNotBlank(id)){
            entity = dictionaryRep.getOne(id);
        }
        String typeId = vo.getTypeId();
        DsDictionaryType dictionaryType = dictionaryTypeRep.getOne(typeId);
        entity.setDictionaryType(dictionaryType);
        AbstractMyBeanUtils.copyProperties(vo, entity);
        dictionaryRep.save(entity);
        return Result.ok();
    }

    @Override
    public Result getOne(String id) {
        DsDictionary entity = dictionaryRep.getOne(id);
        DsDictionaryVo vo = DsDictionaryVo.convert(entity);
        return Result.ok(vo);
    }

    @Override
    public void delete(String id) {
        dictionaryRep.deleteById(id);
    }

    @Override
    public Result listAll() {
        return null;
    }

    @Override
    public Result listPage(Map<String, String> params, Pageable pageable) {
        Page<DsDictionary> page = dictionaryRep.findAll(new Specification<DsDictionary>() {
            @Override
            public Predicate toPredicate(Root<DsDictionary> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.conjunction();
                if(StringUtils.isNotEmpty(params.get("typeId"))){
                    predicate.getExpressions().add(criteriaBuilder.and(root.<DsDictionaryType>get("dictionaryType").get("id").in(params.get("typeId"))));
                }
                if(StringUtils.isNotEmpty(params.get("code"))){
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("code").as(String.class), "%"+ params.get("code").trim().replaceAll("/","//").replaceAll("_","/_").replaceAll("%","/%")+"%",'/'));
                }
                if(StringUtils.isNotEmpty(params.get("name"))){
                    predicate.getExpressions().add(criteriaBuilder.like(root.get("name").as(String.class), "%"+ params.get("name").trim().replaceAll("/","//").replaceAll("_","/_").replaceAll("%","/%")+"%",'/'));
                }
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("sort")));
                return predicate;
            }
        }, pageable);
        List<DsDictionaryVo> list = DsDictionaryVo.convert(page.getContent());
        return Result.ok(PageResult.setPageResult(pageable, page.getTotalElements(), list));
    }

    @Override
    public Map<String, String> listByTypeId(String typeId) {
        List<DsDictionary> list = dictionaryRep.listByTypeId(typeId);
        Map<String, String> dictMap = Maps.newHashMap();
        for (DsDictionary entity : list){
            dictMap.put(entity.getCode(), entity.getName());
        }
        return dictMap;
    }
}
