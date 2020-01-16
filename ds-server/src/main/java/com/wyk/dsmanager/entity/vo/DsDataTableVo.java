package com.wyk.dsmanager.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.wyk.dsmanager.entity.DsDataTable;
import com.wyk.dsmanager.entity.DsDataTableColumn;
import com.wyk.dsmanager.entity.DsDatabase;
import com.wyk.dsmanager.utils.AbstractMyBeanUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/7
 * \*
 * \* Description
 *
 * @author wyk
 */
@Data
@NoArgsConstructor
public class DsDataTableVo {
    private String id;
    private String dtName;
    private String dtNameCn;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
    private String dbId;
    private String resId;
    private List<DsDataTableColumnVo> columns = Lists.newLinkedList();

    public DsDataTableVo(String id, String dtNameCn) {
        this.id = id;
        this.dtNameCn = dtNameCn;
    }

    public static DsDataTableVo convert(DsDataTable entity) {
        if (null == entity) {
            return null;
        }
        DsDataTableVo vo = new DsDataTableVo();
        vo.setDbId(entity.getDsDatabase().getId());
        vo.setResId(entity.getDsDataResource().getId());
        AbstractMyBeanUtils.copyProperties(entity, vo);
        return vo;
    }

    public static List<DsDataTableVo> convert(List<DsDataTable> entities){
        List<DsDataTableVo> vos = Lists.newArrayList();
        for (DsDataTable entity : entities) {
            vos.add(convert(entity));
        }
        return vos;
    }
}
