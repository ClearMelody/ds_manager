package com.waiterlong.vipmis.domain.vo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.Label;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import lombok.Data;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/10/2
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Data
public class LabelVo {
    private String id;

    private String name;

    public static LabelVo convertLabel(Label label) {
        if (null == label) {
            return null;
        }
        LabelVo labelVo = new LabelVo();
        AbstractMyBeanUtils.copyProperties(label, labelVo);
        return labelVo;
    }
    public static List<LabelVo> convertLabel(List<Label> labels){
        List<LabelVo> labelVos = Lists.newArrayList();
        for (Label label : labels) {
            labelVos.add(convertLabel(label));
        }
        return labelVos;
    }
}
