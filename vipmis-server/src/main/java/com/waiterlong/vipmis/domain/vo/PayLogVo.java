package com.waiterlong.vipmis.domain.vo;

import com.google.common.collect.Lists;
import com.waiterlong.vipmis.domain.PayLog;
import com.waiterlong.vipmis.domain.User;
import com.waiterlong.vipmis.utils.AbstractMyBeanUtils;
import lombok.Data;

import javax.persistence.Column;
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
public class PayLogVo {
    private String id;

    private Long payBill;

    private Date payDate;

    private Date payDesc;
    
    private UserInfoVo userInfoVo;

    public static PayLogVo convertPayLog(PayLog payLog) {
        if (null == payLog) {
            return null;
        }
        PayLogVo payLogVo = new PayLogVo();
        AbstractMyBeanUtils.copyProperties(payLog, payLogVo);
        payLogVo.setUserInfoVo(UserInfoVo.convertUser(payLog.getUser()));
        return payLogVo;
    }
    public static List<PayLogVo> convertPayLog(List<PayLog> payLogs){
        List<PayLogVo> payLogVos = Lists.newArrayList();
        for (PayLog payLog : payLogs) {
            payLogVos.add(convertPayLog(payLog));
        }
        return payLogVos;
    }
}
