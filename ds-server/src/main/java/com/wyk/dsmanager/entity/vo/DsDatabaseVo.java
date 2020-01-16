package com.wyk.dsmanager.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.wyk.dsmanager.entity.DsDatabase;
import com.wyk.dsmanager.utils.AbstractMyBeanUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2019/12/31
 * \*
 * \* Description
 *
 * @author wyk
 */
@Data
@NoArgsConstructor
public class DsDatabaseVo {
    private String id;
    private String dbName;
    private String dbType;
    private String dbUrl;
    private String dbUsername;
    private String dbPassword;
    private byte dbUsable;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    public DsDatabaseVo(String id, String dbName) {
        this.id = id;
        this.dbName = dbName;
    }

    public DsDatabaseVo(String id, String dbName, String dbType, String dbUrl, String dbUsername, byte dbUsable,
                        LocalDateTime createTime) {
        this.id = id;
        this.dbName = dbName;
        this.dbType = dbType;
        this.dbUrl = dbUrl;
        this.dbUsername = dbUsername;
        this.dbUsable = dbUsable;
        this.createTime = createTime;
    }

    public static DsDatabaseVo convert(DsDatabase entity) {
        if (null == entity) {
            return null;
        }
        DsDatabaseVo vo = new DsDatabaseVo();
        AbstractMyBeanUtils.copyProperties(entity, vo);
        return vo;
    }

    public static List<DsDatabaseVo> convert(List<DsDatabase> entities){
        List<DsDatabaseVo> vos = Lists.newArrayList();
        for (DsDatabase entity : entities) {
            vos.add(convert(entity));
        }
        return vos;
    }
}
