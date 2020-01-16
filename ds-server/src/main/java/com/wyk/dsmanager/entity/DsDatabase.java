package com.wyk.dsmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
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
@Entity
@Table(name = "DS_DATABASE")
@JsonIgnoreProperties(ignoreUnknown = true, value =
        {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class DsDatabase implements Serializable {
    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "db_name")
    private String dbName;
    @Column(name = "db_type")
    private String dbType;
    @Column(name = "db_url")
    private String dbUrl;
    @Column(name = "db_username")
    private String dbUsername;
    @Column(name = "db_password")
    private String dbPassword;
    @Column(name = "db_usable")
    private byte dbUsable;
    @Column(name = "create_time")
    private LocalDateTime createTime;
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "dsDatabase", cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonIgnoreProperties(value = "dsDatabase")
    private List<DsDataTable> dsDataTables = Lists.newLinkedList();
}
