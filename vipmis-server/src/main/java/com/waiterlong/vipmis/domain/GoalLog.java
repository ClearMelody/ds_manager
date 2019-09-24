package com.waiterlong.vipmis.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-07-16 09:15
 * \*
 * \* Description: 积分实体
 * \
 *
 * @author waiterlong
 */
@Entity
@Table(name = "goal_log")
@Data
public class GoalLog {
    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    @Column(name = "id")
    private String id;

    @Column(name = "goal_change")
    private Long goalChange;

    @Column(name = "create_time")
    private Date createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GoalLog goalLog = (GoalLog) o;
        return Objects.equals(id, goalLog.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
