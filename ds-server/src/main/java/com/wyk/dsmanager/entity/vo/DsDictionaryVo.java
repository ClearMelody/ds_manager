package com.wyk.dsmanager.entity.vo;

import com.google.common.collect.Lists;
import com.wyk.dsmanager.entity.DsDictionary;
import com.wyk.dsmanager.utils.AbstractMyBeanUtils;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/13
 * \*
 * \* Description
 *
 * @author wyk
 */
@Data
public class DsDictionaryVo implements Serializable {
    private String id;
    private String code;
    private String pcode;
    private String name;
    private String sort;
    private String typeId;

    public static DsDictionaryVo convert(DsDictionary entity) {
        if (null == entity) {
            return null;
        }
        DsDictionaryVo vo = new DsDictionaryVo();
        AbstractMyBeanUtils.copyProperties(entity, vo);
        return vo;
    }

    public static List<DsDictionaryVo> convert(List<DsDictionary> entities){
        List<DsDictionaryVo> vos = Lists.newArrayList();
        for (DsDictionary entity : entities) {
            vos.add(convert(entity));
        }
        return vos;
    }
}
