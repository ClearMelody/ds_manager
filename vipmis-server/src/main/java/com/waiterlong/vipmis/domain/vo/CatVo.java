package com.waiterlong.vipmis.domain.vo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.Cat;
import com.waiterlong.vipmis.domain.User;
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
public class CatVo {
    private String id;

    private String img;

    private String name;

    private String birthday;

    /**
     * 公猫 母猫
     */
    private String sex;

    /**
     * 品种 英国短毛猫金渐层色
     */
    private String breed;

    /**
     * 是否绝育 未绝育
     */
    private String sterilization;

    /**
     * 疫苗初次时间
     */
    private String vaccineFirst;

    /**
     * 疫苗最近时间
     */
    private String vaccineLast;

    /**
     * 疫苗下次时间
     */
    private String vaccineNext;

    /**
     * 主人
     */
    private UserInfoVo userInfoVo;
    
    public static List<CatVo> convertCat(List<Cat> cats){
        List<CatVo> catVos = Lists.newArrayList();
        for (Cat cat : cats) {
            catVos.add(convertCat(cat));
        }
        return catVos;
    }

    public static CatVo convertCat(Cat cat) {
        if (null == cat) {
            return null;
        }
        CatVo catVo = new CatVo();
        AbstractMyBeanUtils.copyProperties(cat, catVo);
        catVo.setUserInfoVo(UserInfoVo.convertUser(cat.getUser()));
        return catVo;
    }
}
