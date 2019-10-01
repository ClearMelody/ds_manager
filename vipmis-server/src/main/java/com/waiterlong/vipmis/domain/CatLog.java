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
