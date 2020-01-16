package com.wyk.dsmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * \* Date 2020/01/02
 * \*
 * \* Description
 *
 * @author wyk
 */
@Data
@Entity
@Table(name = "DS_DATA_RESOURCE")
@JsonIgnoreProperties(ignoreUnknown = true, value =
        {"hibernateLazyInitializer", "handler", "fieldHandler"})
public class DsDataResource implements Serializable {
    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "ds_name")
    private String dsName;
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @OneToMany(mappedBy = "dsDataResource", cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @JsonIgnore
    @JsonIgnoreProperties(value = "dsDataResource")
    private List<DsDataTable> dsDataTables = Lists.newLinkedList();
}
