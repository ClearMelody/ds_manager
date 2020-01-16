package com.wyk.dsmanager.entity.vo;

import com.google.common.collect.Lists;
import com.wyk.dsmanager.entity.DsDataTableColumn;
import com.wyk.dsmanager.utils.AbstractMyBeanUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/8
 * \*
 * \* Description
 *
 * @author wyk
 */
@Data
@NoArgsConstructor
public class DsDataTableColumnVo {
    private String id;
    private String columnName;
    private String columnTitle;
    private byte visiable;
    private String dictId;
    private String isNullable;
    private String dataType;
    private String columnKey;
    private String columnComment;

    public static DsDataTableColumnVo convert(DsDataTableColumn entity) {
        if (null == entity) {
            return null;
        }
        DsDataTableColumnVo vo = new DsDataTableColumnVo();
        AbstractMyBeanUtils.copyProperties(entity, vo);
        return vo;
    }

    public static List<DsDataTableColumnVo> convert(List<DsDataTableColumn> entities){
        List<DsDataTableColumnVo> vos = Lists.newArrayList();
        for (DsDataTableColumn entity : entities) {
            vos.add(convert(entity));
        }
        return vos;
    }
}
