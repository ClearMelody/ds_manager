package com.waiterlong.vipmis.service.impl;

import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.SysPermission;
import com.waiterlong.vipmis.domain.vo.SysPermissionVo;
import com.waiterlong.vipmis.repository.SysPermissionRep;
import com.waiterlong.vipmis.service.ISysPermissionService;
import com.waiterlong.vipmis.service.base.BaseServiceImpl;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2019/12/2
 * \*
 * \* Description
 *
 * @author wyk
 */
@Service("iSysPermissionService")
public class SysPermissionServiceImpl extends BaseServiceImpl implements ISysPermissionService {
    @Resource(name = "sysPermissionRep")
    private SysPermissionRep sysPermissionRep;

    /**
     * 查询所有权限菜单/按钮，递归遍历为树形结构数据
     * @return
     */
    @Override
    public Result queryAllPermissions(SysPermission sysPermission) {
        List<SysPermission> list = sysPermissionRep.findAll(new Specification<SysPermission>(){
            @Override
            public Predicate toPredicate(Root<SysPermission> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> list = new ArrayList<Predicate>();
                if(StringUtils.isNotEmpty(sysPermission.getName())){
                    list.add(criteriaBuilder.like(root.get("name").as(String.class), "%"+ sysPermission.getName().trim().replaceAll("/","//").replaceAll("_","/_").replaceAll("%","/%")+"%",'/'));
                }
                if(StringUtils.isNotEmpty(sysPermission.getPermission())){
                    list.add(criteriaBuilder.like(root.get("permission").as(String.class), "%"+ sysPermission.getPermission().trim().replaceAll("/","//").replaceAll("_","/_").replaceAll("%","/%")+"%",'/'));
                }
                if(StringUtils.isNotEmpty(sysPermission.getHref())){
                    list.add(criteriaBuilder.like(root.get("href").as(String.class), "%"+ sysPermission.getHref().trim().replaceAll("/","//").replaceAll("_","/_").replaceAll("%","/%")+"%",'/'));
                }
                if(sysPermission.getType() != null){
                    list.add(criteriaBuilder.equal(root.get("type"), sysPermission.getType()));
                }
                Predicate[] p = new Predicate[list.size()];
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get("sort")));
                return criteriaBuilder.and(list.toArray(p));
            }
        });
        //List<SysPermissionVo> permissionVos = getPermissionListByPid(null, list);
        List<SysPermissionVo> permissionVos = getNonePidPermissionList(list);
        permissionVos = loopPermissions(permissionVos, list);
        return Result.ok(permissionVos);
    }

    /**
     * 根据id查询权限实体
     * @param id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result queryOneEntity(String id) {
        Optional<SysPermission> optional = sysPermissionRep.findById(id);
        SysPermission sysPermission = optional.get();
        SysPermissionVo sysPermissionVo = SysPermissionVo.convertSysPermission(sysPermission);
        if(sysPermission.getPid() != null){
            Optional<SysPermission> pOptional = sysPermissionRep.findById(sysPermission.getPid());
            if(pOptional.get() != null){
                sysPermissionVo.setPname(pOptional.get().getName());
            }
        }
        return Result.ok(sysPermissionVo);
    }

    /**
     * 保存或更新权限
     * @param sysPermissionVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result saveOrUpdate(@NotNull SysPermissionVo sysPermissionVo) {
        String id = sysPermissionVo.getId();
        if(StringUtils.isNotBlank(id)){
            Optional<SysPermission> optional = sysPermissionRep.findById(id);
            if(!optional.isPresent()){
                return Result.badArgumentValue();
            }
        }else{
           id =  UUID.randomUUID().toString().replaceAll("-", "");
           sysPermissionVo.setId(id);
           String path = sysPermissionVo.getPath();
           if(StringUtils.isNotBlank(path)){
               sysPermissionVo.setPath(path + "," + id);
           }else{
               sysPermissionVo.setPath(id);
           }
        }
        SysPermission sysPermission = new SysPermission();
        AbstractMyBeanUtils.copyProperties(sysPermissionVo, sysPermission);
        sysPermissionRep.save(sysPermission);
        return Result.ok();
    }

    @Override
    public Result deleteAllByOneId(String id) {
        if(StringUtils.isBlank(id)){
            return Result.badArgumentValue();
        }
        sysPermissionRep.deleteAllByOneId(id);
        return Result.ok();
    }

    /**
     * 遍历获取当前list中没有父节点的对象
     * @param permissions
     * @return
     */
    private List<SysPermissionVo> getNonePidPermissionList(List<SysPermission> permissions){
        List<SysPermissionVo> children = new ArrayList<>();
        for (SysPermission permission : permissions) {
            boolean pExist = false;
            for(SysPermission temp : permissions){
                if(temp.getId().equals(permission.getPid())){
                    pExist = true;break;
                }
            }
            if(!pExist){
                SysPermissionVo sysPermissionVo= SysPermissionVo.convertSysPermission(permission);
                children.add(sysPermissionVo);
            }
        }
        return children;
    }

    /**
     * 根据pid获取children
     * @param id
     * @param permissions
     * @return
     */
    private List<SysPermissionVo> getPermissionListByPid(String id,List<SysPermission> permissions){
        List<SysPermissionVo> children = new ArrayList<>();
        for (SysPermission permission : permissions) {
            boolean checkParam = (id == null && null == permission.getPid()) || (id != null && id.equals(permission.getPid()));
            if(checkParam){
                SysPermissionVo temp= SysPermissionVo.convertSysPermission(permission);
                children.add(temp);
            }
        }
        return children;
    }

    /**
     * 递归遍历所有节点
     * @param permissionVos
     * @param permissions
     * @return
     */
    private List<SysPermissionVo> loopPermissions(List<SysPermissionVo> permissionVos, List<SysPermission> permissions) {

        for (SysPermissionVo permissionVo : permissionVos) {
            permissionVo.setChildren(getPermissionListByPid(permissionVo.getId(), permissions));
            if(permissionVo.getChildren().isEmpty()) {
                continue;
            }
            loopPermissions(permissionVo.getChildren(), permissions);
        }
        return permissionVos;
    }


}
