package com.wyk.dsmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/13
 * \*
 * \* Description
 *
 * @author wyk
 */
@Data
@Entity
@Table(name = "ds_dictionary")
public class DsDictionary{
    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    @Column(name = "id")
    private String id;
    @Column(name = "code")
    private String code;
    @Column(name = "pcode")
    private String pcode;
    @Column(name = "name")
    private String name;
    @Column(name = "sort")
    private String sort;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id",nullable = false, referencedColumnName = "id")
    @JsonIgnoreProperties(value = "dictionaries")
    private DsDictionaryType dictionaryType;
}
