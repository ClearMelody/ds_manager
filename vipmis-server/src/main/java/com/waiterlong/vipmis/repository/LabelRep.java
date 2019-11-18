package com.waiterlong.vipmis.repository;

import com.waiterlong.vipmis.domain.Label;
import com.waiterlong.vipmis.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/11/18
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Repository("labelRep")
public interface LabelRep extends JpaRepository<Label, String>{
    Page<Label> findByNameIsContainingOrderByCreateTimeDesc(String name, Pageable pageable);
}
