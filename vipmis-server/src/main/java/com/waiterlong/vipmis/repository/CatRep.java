package com.waiterlong.vipmis.repository;

import com.waiterlong.vipmis.domain.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/11/21
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Repository("catRep")
public interface CatRep extends JpaRepository<Cat, String> {
}
