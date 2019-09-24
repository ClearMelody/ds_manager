package com.waiterlong.vipmis.assets;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: 龙威
 * \* Date: 2019/09/06
 * \* Time: 16:48
 * \* Description: 微信API的URL
 * \
 */
public class WeiXinApiUrl {
    public final static String ERRCODE_KEY = "errcode";
    public final static String ERRMSG_KEY = "errmsg";
    public final static String ACCESS_TOKEN_KEY = "access_token";
    public final static String DATA_KEY = "data";

    public final static String GET_USER_APPID = "https://api.weixin.qq.com/sns/jscode2session";
    public final static String GET_ACCESSTOKEN = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=";
    public final static String GET_COMPANY_ACCESSTOKEN = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
    public final static String POST_USERID_2_OPENID = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_openid?access_token=";
    public final static String POST_OPENID_2_USERID = "https://qyapi.weixin.qq.com/cgi-bin/user/convert_to_userid?access_token=";
}
