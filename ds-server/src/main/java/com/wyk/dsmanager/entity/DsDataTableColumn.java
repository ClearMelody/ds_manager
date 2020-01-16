package com.wyk.dsmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/8
 * \*
 * \* Description
 *
 * @author wyk
 */
@Data
@Entity
@Table(name = "DS_DATA_TABLE_COLUMN")
@JsonIgnoreProperties(ignoreUnknown = true, value =
        {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class DsDataTableColumn implements Serializable {
    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "column_name")
    private String columnName;
    @Column(name = "column_title")
    private String columnTitle;
    @Column(name = "data_type")
    private String dataType;
    @Column(name = "visiable")
    private byte visiable;
    @Column(name = "dict_id")
    private String dictId;

    @ManyToOne(fetch = FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
    @JoinColumn(name = "table_id", referencedColumnName = "id")
    @JsonIgnore
    @JsonIgnoreProperties(value = "columns")
    private DsDataTable dsDataTable;

}
