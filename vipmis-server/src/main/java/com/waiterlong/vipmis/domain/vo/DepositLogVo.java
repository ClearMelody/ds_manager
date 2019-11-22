package com.waiterlong.vipmis.domain.vo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.DepositLog;
import com.waiterlong.vipmis.domain.DepositLog;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
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
public class DepositLogVo {
    private String id;

    private UserInfoVo userInfoVo;

    private String title;

    private Date time;

    private BigDecimal value;

    public static DepositLogVo convertDepositLog(DepositLog depositLog) {
        if (null == depositLog) {
            return null;
        }
        DepositLogVo depositLogVo = new DepositLogVo();
        AbstractMyBeanUtils.copyProperties(depositLog, depositLogVo);
        depositLogVo.setUserInfoVo(UserInfoVo.convertUser(depositLog.getUser()));
        return depositLogVo;
    }
    public static List<DepositLogVo> convertDepositLog(List<DepositLog> depositLogs){
        List<DepositLogVo> depositLogVos = Lists.newArrayList();
        for (DepositLog depositLog : depositLogs) {
            depositLogVos.add(convertDepositLog(depositLog));
        }
        return depositLogVos;
    }
}
