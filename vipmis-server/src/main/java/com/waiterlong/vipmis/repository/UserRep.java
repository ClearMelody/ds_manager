package com.waiterlong.vipmis.repository;

import com.waiterlong.vipmis.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019-07-16 09:15
 * \*
 * \* Description:
 * \
 *
 * @author waiterlong
 */
@Repository("userRep")
public interface UserRep extends JpaRepository<User, String> {
    /**
     * 分页列出用户
     *
     * @param realName {String}
     * @param pageable {Pageable}
     * @return {Page<User>}
     */
    Page<User> findByRealNameIsContainingOrderByRegisterTimeDesc(String realName, Pageable pageable);

    /**
     * 根据openid找用户
     *
     * @param weChatOpenId {String}
     * @return {User}
     */
    User findByWeChatOpenId(String weChatOpenId);
}
