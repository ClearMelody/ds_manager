package com.wyk.dsmanager.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.wyk.dsmanager.entity.DsDictionaryType;
import com.wyk.dsmanager.utils.AbstractMyBeanUtils;
import lombok.Data;
import java.time.LocalDateTime;
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
public class DsDictionaryTypeVo {
    private String id;
    private String pid;
    private String path;
    private String name;
    private String sort;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    private List<DsDictionaryTypeVo> children;

    public static DsDictionaryTypeVo convert(DsDictionaryType entity) {
        if (null == entity) {
            return null;
        }
        DsDictionaryTypeVo vo = new DsDictionaryTypeVo();
        AbstractMyBeanUtils.copyProperties(entity, vo);
        return vo;
    }

    public static List<DsDictionaryTypeVo> convert(List<DsDictionaryType> entities){
        List<DsDictionaryTypeVo> vos = Lists.newArrayList();
        for (DsDictionaryType entity : entities) {
            vos.add(convert(entity));
        }
        return vos;
    }
}
