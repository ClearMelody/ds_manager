package com.wyk.dsmanager.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.common.collect.Lists;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
@Table(name = "ds_dictionary_type")
public class DsDictionaryType {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "pid")
    private String pid;
    @Column(name = "path", length = 2000)
    private String path;
    @Column(name = "name")
    private String name;
    @Column(name = "sort")
    private String sort;
    @Column(name = "create_time")
    private LocalDateTime createTime;

    @OneToMany(mappedBy = "dictionaryType", fetch = FetchType.LAZY,cascade={CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REMOVE})
    @JsonIgnoreProperties(value = "dictionaryType")
    private List<DsDictionary> dictionaries = Lists.newLinkedList();

}
