//package com.waiterlong.vipmis.domain;
//
//import lombok.Data;
//import org.hibernate.annotations.GenericGenerator;
//
//import javax.persistence.*;
//
///**
// * \* Created with IntelliJ IDEA
// * \* Date 2019/12/6
// * \*
// * \* Description
// *
// * @author wyk
// */
//@Data
//@Entity
//@Table(name = "sys_role_permission")
//public class SysRolePermission {
//    @Id
//    @GenericGenerator(name = "system_uuid", strategy = "uuid")
//    @GeneratedValue(generator = "system_uuid")
//    @Column(name = "id")
//    private String id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "role_id")
//    private SysRole sysRole;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "permission_id")
//    private SysPermission sysPermission;
//}
