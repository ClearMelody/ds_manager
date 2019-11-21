package com.waiterlong.vipmis.domain.wxvo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.Cat;
import com.waiterlong.vipmis.domain.User;
import com.waiterlong.vipmis.domain.vo.CatLogVo;
import com.waiterlong.vipmis.domain.vo.CatVo;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import lombok.Data;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/11/21
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Data
public class WxCatVo {
    private String id;
    private String img;
    private String name;
    private String birthday;
    private String sex;
    private String breed;
    private String sterilization;
    private String vaccineFirst;
    private String vaccineLast;
    private String vaccineNext;
    private List<CatLogVo> list;

    public static WxCatVo convertCat(Cat cat) {
        if (null == cat) {
            return null;
        }
        WxCatVo wxCatVo = new WxCatVo();
        AbstractMyBeanUtils.copyProperties(cat, wxCatVo);
        wxCatVo.setList(CatLogVo.convertCatLog(cat.getCatLogs()));
        return wxCatVo;
    }

    public static List<WxCatVo> convertCat(List<Cat> cats){
        List<WxCatVo> wxCatVos = Lists.newArrayList();
        for (Cat cat : cats) {
            wxCatVos.add(convertCat(cat));
        }
        return wxCatVos;
    }
}
