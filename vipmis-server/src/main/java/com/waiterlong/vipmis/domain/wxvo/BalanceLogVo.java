package com.waiterlong.vipmis.domain.wxvo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.DepositLog;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import com.waiterlong.vipmis.utils.DateUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/10/8
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Data
public class BalanceLogVo {
    private String title;

    private String time;

    private BigDecimal value;

    public static BalanceLogVo convertDepositLog(DepositLog depositLog) {
        if (null == depositLog) {
            return null;
        }
        BalanceLogVo balanceLogVo = new BalanceLogVo();
        AbstractMyBeanUtils.copyProperties(depositLog, balanceLogVo);
        balanceLogVo.setTime(DateUtil.dateToString(depositLog.getCreateTime(), DateUtil.FMT_YYYY_MM_DD));
        return balanceLogVo;
    }
    public static List<BalanceLogVo> convertDepositLog(List<DepositLog> depositLogs){
        List<BalanceLogVo> balanceLogVos = Lists.newArrayList();
        for (DepositLog depositLog : depositLogs) {
            balanceLogVos.add(convertDepositLog(depositLog));
        }
        return balanceLogVos;
    }
}
