package com.waiterlong.vipmis.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
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
@Table(name = "sys_user")
@Data
public class SysUser {
    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "create_time")
    private Date createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private SysRole sysRole;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysUser sysUser = (SysUser) o;
        return Objects.equals(id, sysUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
