package com.waiterlong.vipmis.domain.vo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.CardDescription;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/12/15
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Data
public class CardDescriptionVo {
    private String id;

    private String content;

    private Date createTime;

    public static List<CardDescriptionVo> convertCardDescription(List<CardDescription> cardDescriptions){
        List<CardDescriptionVo> cardDescriptionVos = Lists.newArrayList();
        for (CardDescription cardDescription : cardDescriptions) {
            cardDescriptionVos.add(convertCardDescription(cardDescription));
        }
        return cardDescriptionVos;
    }

    public static CardDescriptionVo convertCardDescription(CardDescription cardDescription) {
        if (null == cardDescription) {
            return null;
        }
        CardDescriptionVo cardDescriptionInfoVo = new CardDescriptionVo();
        AbstractMyBeanUtils.copyProperties(cardDescription, cardDescriptionInfoVo);
        return cardDescriptionInfoVo;
    }
}
