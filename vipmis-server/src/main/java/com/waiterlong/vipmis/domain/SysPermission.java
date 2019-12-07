package com.waiterlong.vipmis.domain;

import com.google.common.collect.Lists;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2019/12/2
 * \*
 * \* Description
 *
 * @author wyk
 */
@Entity
@Table(name = "sys_permission")
@Data
public class SysPermission {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "pid")
    private String pid;
    @Column(name = "name")
    private String name;
    @Column(name = "href")
    private String href;
    @Column(name = "permission")
    private String permission;
    @Column(name = "path")
    private String path;
    @Column(name = "type")
    private Integer type;
    @Column(name = "sort")
    private Integer sort;

    @ManyToMany(mappedBy = "sysPermissions")
    private List<SysRole> sysRoles;
}
