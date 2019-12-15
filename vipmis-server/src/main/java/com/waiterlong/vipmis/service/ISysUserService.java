package com.waiterlong.vipmis.service;

import com.waiterlong.vipmis.component.Result;
import com.waiterlong.vipmis.domain.vo.SysUserVo;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2019/12/9
 * \*
 * \* Description
 *
 * @author wyk
 */
public interface ISysUserService {
    Result findSysUserInfoById(String id);
    Result addSysUser(SysUserVo sysUserVo);
    Result listSysUsersByPage(Map<String, Object> paramMap, Pageable pageable);
}
