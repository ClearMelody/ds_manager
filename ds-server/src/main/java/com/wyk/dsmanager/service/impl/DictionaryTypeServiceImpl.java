package com.wyk.dsmanager.service.impl;

import com.wyk.dsmanager.entity.DsDictionary;
import com.wyk.dsmanager.entity.DsDictionaryType;
import com.wyk.dsmanager.entity.vo.DsDictionaryTypeVo;
import com.wyk.dsmanager.repository.DictionaryTypeRep;
import com.wyk.dsmanager.service.IDictionaryTypeService;
import com.wyk.dsmanager.utils.AbstractMyBeanUtils;
import com.wyk.dsmanager.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/13
 * \*
 * \* Description
 *
 * @author wyk
 */
@Repository("dictionaryTypeService")
public class DictionaryTypeServiceImpl implements IDictionaryTypeService {
    @Resource(name = "dictionaryTypeRep")
    DictionaryTypeRep dictionaryTypeRep;

    @Override
    public Result save(DsDictionaryTypeVo vo) {
        String id = vo.getId();
        DsDictionaryType dictionaryType = new DsDictionaryType();
        if(StringUtils.isNotBlank(id)){
            dictionaryType = dictionaryTypeRep.getOne(id);
        }else{
            id =  UUID.randomUUID().toString().replaceAll("-", "");
            dictionaryType.setId(id);
            String path = vo.getPath();
            if(StringUtils.isNotBlank(path)){
                vo.setPath(path + "," + id);
            }else{
                vo.setPath(id);
            }
        }
        AbstractMyBeanUtils.copyProperties(vo, dictionaryType);
        dictionaryTypeRep.save(dictionaryType);
        return Result.ok();
    }

    @Override
    public Result getOne(String id) {
        DsDictionaryType entity = dictionaryTypeRep.getOne(id);
        DsDictionaryTypeVo vo = DsDictionaryTypeVo.convert(entity);
        return Result.ok(vo);
    }

    @Override
    public void delete(String id) {
        dictionaryTypeRep.deleteAllByOneId(id);
    }

    @Override
    public Result listAll() {
        List<DsDictionaryType> list = dictionaryTypeRep.findByOrderBySortAscCreateTimeAsc();
        List<DsDictionaryTypeVo> vos = getNonePidList(list);
        vos = loop(vos, list);
        return Result.ok(vos);
    }

    /**
     * 遍历获取当前list中没有父节点的对象
     * @param entities
     * @return
     */
    public static List<DsDictionaryTypeVo> getNonePidList(List<DsDictionaryType> entities){
        List<DsDictionaryTypeVo> children = new ArrayList<>();
        for (DsDictionaryType entity : entities) {
            boolean pExist = false;
            for(DsDictionaryType temp : entities){
                if(temp.getId().equals(entity.getPid())){
                    pExist = true;break;
                }
            }
            if(!pExist){
                DsDictionaryTypeVo vo = DsDictionaryTypeVo.convert(entity);
                children.add(vo);
            }
        }
        if(children.size() == 0){
            return null;
        }
        return children;
    }

    /**
     * 根据pid获取children
     * @param id
     * @param entities
     * @return
     */
    public static List<DsDictionaryTypeVo> getListByPid(String id, List<DsDictionaryType> entities){
        List<DsDictionaryTypeVo> children = new ArrayList<>();
        for (DsDictionaryType entity : entities) {
            boolean checkParam = (id == null && null == entity.getPid()) || (id != null && id.equals(entity.getPid()));
            if(checkParam){
                DsDictionaryTypeVo temp= DsDictionaryTypeVo.convert(entity);
                children.add(temp);
            }
        }
        if(children.size() == 0){
            return null;
        }
        return children;
    }

    /**
     * 递归遍历所有节点
     * @param vos
     * @param entites
     * @return
     */
    public static List<DsDictionaryTypeVo> loop(List<DsDictionaryTypeVo> vos, List<DsDictionaryType> entites) {

        for (DsDictionaryTypeVo vo : vos) {
            vo.setChildren(getListByPid(vo.getId(), entites));
            if(vo.getChildren() == null) {
                continue;
            }
            loop(vo.getChildren(), entites);
        }
        return vos;
    }
}
