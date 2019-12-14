package com.waiterlong.vipmis.repository;

import com.waiterlong.vipmis.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
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
    Page<User> findByRealNameContainsAndCardCordContainsAndIdCardContainsAndPhoneContainsAndWeChatNameContainsOrderByRegisterTimeDesc(String realName, String cardCord, String idCard, String phone, String weChatName, Pageable pageable);
    Page<User> findByRealNameContainsAndCardCordContainsAndIdCardContainsAndPhoneContainsAndWeChatNameContainsAndLabel_IdIsOrderByRegisterTimeDesc(String realName, String cardCord, String idCard, String phone, String weChatName, String labelId, Pageable pageable);

    /**
     * 根据openid找用户
     *
     * @param weChatOpenId {String}
     * @return {User}
     */
    User findByWeChatOpenId(String weChatOpenId);

    @Query(nativeQuery = true, value = "select count(t.time) as sum, t.time as time from (select id, date_format(register_time, '%Y-%m-%d') as time from user where register_time between ? and ?) t GROUP BY t.time order by unix_timestamp(t.time) asc;")
    List<Map<String, Object>> getUserGrowth(String start, String end);

    Long countByRegisterTimeLessThan(Date time);
}
