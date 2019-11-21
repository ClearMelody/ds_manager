package com.waiterlong.vipmis.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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
@Table(name = "cat_log")
@Data
public class CatLog {
    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    @Column(name = "id")
    private String id;

    /**
     * 洗澡时间
     */
    @Column(name = "wash_time")
    private String washTime;

    /**
     * 体重
     */
    @Column(name = "weight")
    private String weight;

    /**
     * 建议体内驱虫时间
     */
    @Column(name = "vivo")
    private String vivo;

    /**
     * 建议体外驱虫时间
     */
    @Column(name = "vitro")
    private String vitro;

    @ManyToOne(fetch = FetchType.LAZY)
    private Cat cat;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatLog catLog = (CatLog) o;
        return Objects.equals(id, catLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
