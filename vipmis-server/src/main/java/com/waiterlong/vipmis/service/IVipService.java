package com.waiterlong.vipmis.service;

import com.waiterlong.vipmis.domain.wxvo.VipVo;
import com.waiterlong.vipmis.service.base.IBaseService;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/10/8
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
public interface IVipService extends IBaseService {
    /**
     * 根据openid找VIP
     * @param openid {String}
     * @return Object
     */
    Object findVipByOpenId(String openid);

    /**
     * 更新VIP信息
     * @param vipVo VipVO
     * @return Object
     */
    Object updateVipInfo(VipVo vipVo);

    /**
     * 分页列出余额变化
     * @param paramMap Map<String, Object>
     * @param pageable Pageable
     * @return Object
     */
    Object listDepositLogByPage(Map<String, Object> paramMap, Pageable pageable);

    /**
     * 分页列出积分变化
     * @param paramMap Map<String, Object>
     * @param pageable Pageable
     * @return Object
     */
    Object listGoalLogByPage(Map<String, Object> paramMap, Pageable pageable);
}
