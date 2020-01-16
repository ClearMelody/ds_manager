package com.wyk.dsmanager.entity.vo;

import com.google.common.collect.Lists;
import com.wyk.dsmanager.entity.DsDataResource;
import com.wyk.dsmanager.utils.AbstractMyBeanUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/01/02
 * \*
 * \* Description
 *
 * @author wyk
 */
@Data
@NoArgsConstructor
public class DsDataResourceVo {
    private String id;
    private String dsName;
    private LocalDateTime createTime;

    public DsDataResourceVo(String id, String dsName) {
        this.id = id;
        this.dsName = dsName;
    }

    public static DsDataResourceVo convert(DsDataResource entity) {
        if (null == entity) {
            return null;
        }
        DsDataResourceVo vo = new DsDataResourceVo();
        AbstractMyBeanUtils.copyProperties(entity, vo);
        return vo;
    }

    public static List<DsDataResourceVo> convert(List<DsDataResource> entities){
        List<DsDataResourceVo> vos = Lists.newArrayList();
        for (DsDataResource entity : entities) {
            vos.add(convert(entity));
        }
        return vos;
    }
}
