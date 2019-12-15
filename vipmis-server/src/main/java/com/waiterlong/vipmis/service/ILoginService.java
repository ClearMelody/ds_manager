package com.waiterlong.vipmis.service;

import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.SysUser;
import com.waiterlong.vipmis.domain.vo.SysUserVo;

import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2019/12/14
 * \*
 * \* Description
 *
 * @author wyk
 */
public interface ILoginService {
    SysUserVo getUser(String name);
    Result authLogin(Map<String,Object> params);
    Result getUserInfo();
    Result logout();
}
