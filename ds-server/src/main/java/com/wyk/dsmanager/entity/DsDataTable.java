package com.wyk.dsmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 11:00
 * \*
 * \* Description
 *
 * @author wyk
 */
@Data
@Entity
@Table(name = "DS_DATA_TABLE")
@JsonIgnoreProperties(ignoreUnknown = true, value =
        {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class DsDataTable implements Serializable {
    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "dt_name")
    private String dtName;
    @Column(name = "dt_name_cn")
    private String dtNameCn;
    @Column(name = "create_time")
    private LocalDateTime createTime;
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "res_id",nullable = false, referencedColumnName = "id")
    @JsonIgnoreProperties(value = "dsDataTables")
    private DsDataResource dsDataResource;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "db_id",nullable = false, referencedColumnName = "id")
    @JsonIgnoreProperties(value = "dsDataTables")
    private DsDatabase dsDatabase;

    @OneToMany(mappedBy = "dsDataTable", fetch = FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
    @JsonIgnoreProperties(value = "dsDataTable")
    private List<DsDataTableColumn> columns = Lists.newLinkedList();
}
