package com.waiterlong.vipmis.domain;

import com.google.common.collect.Lists;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
@Table(name = "cat")
@Data
public class Cat {
    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    @Column(name = "id")
    private String id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name="img", columnDefinition="LONGBLOB")
    private String img;

    @Column(name = "name")
    private String name;

    @Column(name = "birthday")
    private String birthday;

    /**
     * 公猫 母猫
     */
    @Column(name = "sex")
    private String sex;

    /**
     * 品种 英国短毛猫金渐层色
     */
    @Column(name = "breed")
    private String breed;

    /**
     * 是否绝育 未绝育
     */
    @Column(name = "sterilization")
    private String sterilization;

    /**
     * 疫苗初次时间
     */
    @Column(name = "vaccine_first")
    private String vaccineFirst;

    /**
     * 疫苗最近时间
     */
    @Column(name = "vaccine_last")
    private String vaccineLast;

    /**
     * 疫苗下次时间
     */
    @Column(name = "vaccine_next")
    private String vaccineNext;

    @OneToMany(mappedBy = "cat", cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, fetch = FetchType.LAZY)
    private List<CatLog> catLogs = Lists.newLinkedList();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return Objects.equals(id, cat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
