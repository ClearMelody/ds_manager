package com.waiterlong.vipmis.service;

import com.waiterlong.vipmis.component.Result;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-04-23 17:04
 * \*
 * \* Description:
 * \
 *
 * @author longwei
 */
public interface IWeiXinApiService {
    /**
     * 获取openid
     *
     * @param code       String
     * @param secretCode String
     * @param appid      String
     * @return Result
     */
    Result getOpenid(String code, String secretCode, String appid);

    /**
     * 更新access_token
     *
     * @return Result
     */
    Result updateAccessToken();

    /**
     * 获取access_token
     *
     * @return String
     */
    String getAccessToken();
}
