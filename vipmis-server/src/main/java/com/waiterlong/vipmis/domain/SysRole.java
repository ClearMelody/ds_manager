package com.waiterlong.vipmis.domain;

import com.google.common.collect.Lists;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/9/29
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Entity
@Table(name = "sys_role")
@Data
public class SysRole {
    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    @Column(name = "id")
    private String id;

    @OneToMany(mappedBy = "sysRole", cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<SysUser> sysUsers = Lists.newLinkedList();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysRole sysRole = (SysRole) o;
        return Objects.equals(id, sysRole.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}