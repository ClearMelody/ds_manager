package com.waiterlong.vipmis.service;

import com.waiterlong.vipmis.service.base.IBaseService;

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
}
