package com.waiterlong.vipmis.service;

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
     * @return Object
     */
    Object getOpenid(String code, String secretCode, String appid);

    /**
     * 更新access_token
     *
     * @return Object
     */
    Object updateAccessToken();

    /**
     * 获取access_token
     *
     * @return String
     */
    String getAccessToken();
}
