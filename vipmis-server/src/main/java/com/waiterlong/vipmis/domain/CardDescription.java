package com.waiterlong.vipmis.domain;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/12/15
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Entity
@Table(name = "card_description")
@Data
public class CardDescription {
    @Id
    @GenericGenerator(name = "system_uuid", strategy = "uuid")
    @GeneratedValue(generator = "system_uuid")
    @Column(name = "id")
    private String id;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name="content")
    private String content;

    @Column(name = "create_time")
    private Date createTime;
}
