package com.waiterlong.vipmis.service;

import com.waiterlong.vipmis.domain.vo.UserVo;
import com.waiterlong.vipmis.service.base.IBaseService;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-07-16 09:15
 * \*
 * \* Description:
 * \
 *
 * @author waiterlong
 */
public interface IUserService extends IBaseService {
    /**
     * 获取用户信息
     * @param id {String}
     * @return Object
     */
    Object findUserInfoById(String id);

    /**
     * 添加用户
     * @param userVo {UserVo}
     * @return Object
     */
    Object addUser(UserVo userVo);

    /**
     * 分页列出用户
     * @param paramMap {Map}
     * @param pageable {Pageable}
     * @return Object
     */
    Object listUsersByPage(Map<String, Object> paramMap, Pageable pageable);
}
