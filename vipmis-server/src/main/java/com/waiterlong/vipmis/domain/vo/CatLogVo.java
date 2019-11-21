package com.waiterlong.vipmis.domain.vo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.CatLog;
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
public class CatLogVo {
    private String id;

    /**
     * 洗澡时间
     */
    private String washTime;

    /**
     * 体重
     */
    private String weight;

    /**
     * 建议体内驱虫时间
     */
    private String vivo;

    /**
     * 建议体外驱虫时间
     */
    private String vitro;

    public static CatLogVo convertCatLog(CatLog catLog) {
        if (null == catLog) {
            return null;
        }
        CatLogVo catLogVo = new CatLogVo();
        AbstractMyBeanUtils.copyProperties(catLog, catLogVo);
        return catLogVo;
    }

    public static List<CatLogVo> convertCatLog(List<CatLog> catLogs){
        List<CatLogVo> catLogVos = Lists.newArrayList();
        for (CatLog catLog : catLogs) {
            catLogVos.add(convertCatLog(catLog));
        }
        return catLogVos;
    }
}
