package com.waiterlong.vipmis.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.waiterlong.vipmis.assets.Constant;
import com.waiterlong.vipmis.assets.WeiXinApiUrl;
import com.waiterlong.vipmis.service.IWeiXinApiService;
import com.waiterlong.vipmis.utils.HttpUtil;
import com.waiterlong.vipmis.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-04-23 17:04
 * \*
 * \* Description:
 * \
 *
 * @author longwei
 */
@Service("iWeiXinApiService")
public class WeiXinApiServiceImpl implements IWeiXinApiService {
    private Logger logger = LoggerFactory.getLogger(WeiXinApiServiceImpl.class);

    @Value("${weixin.miniprogram.secret}")
    private String weixinSecret;

    @Value("${weixin.miniprogram.appid}")
    private String weixinAppid;

    @Override
    public Object getOpenid(@NotNull String code, @NotNull String secretCode, @NotNull String appid) {
        if (code.isEmpty() || secretCode.isEmpty() || appid.isEmpty()) {
            return ResponseUtil.badArgumentValue();
        }
        Map<String, Object> paramMap = Maps.newHashMap();
        paramMap.put("js_code", code);
        paramMap.put("grant_type", "authorization_code");
        paramMap.put("secret", secretCode);
        paramMap.put("appid", appid);
        JSONObject jsonObj;
        try {
            jsonObj = HttpUtil.doGet(WeiXinApiUrl.GET_USER_APPID, paramMap);
            logger.debug("getOpenid data: {}", jsonObj);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseUtil.fail();
        }
        if (!Constant.REQUEST_SUCCESS_CODE.equals(jsonObj.getInteger(WeiXinApiUrl.ERRCODE_KEY))) {
            return ResponseUtil.fail(Constant.REQUEST_FAIL_CODE, jsonObj.getString(WeiXinApiUrl.ERRMSG_KEY));
        }
        return ResponseUtil.ok(jsonObj);
    }

    @Override
    public Object updateAccessToken() {
        JSONObject jsonObject = null;
        try {
            String url = WeiXinApiUrl.GET_ACCESSTOKEN + weixinAppid + "&secret=" + weixinSecret;
            jsonObject = HttpUtil.doGet(url);
            logger.debug("WeiXinApiServiceImpl updateAccessToken data: {}", jsonObject);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        if (null != jsonObject && Constant.REQUEST_SUCCESS_CODE.equals(jsonObject.getInteger(WeiXinApiUrl.ERRCODE_KEY))) {
            Constant.WEIXIN_ACCESSTOKEN = jsonObject.getString(WeiXinApiUrl.ACCESS_TOKEN_KEY);
        }
        if (null == jsonObject || !Constant.REQUEST_SUCCESS_CODE.equals(jsonObject.getInteger(WeiXinApiUrl.ERRCODE_KEY))) {
            return updateAccessToken();
        }
        return ResponseUtil.ok(jsonObject);
    }

    @Override
    public String getAccessToken() {
        return Constant.WEIXIN_ACCESSTOKEN;
    }
}
