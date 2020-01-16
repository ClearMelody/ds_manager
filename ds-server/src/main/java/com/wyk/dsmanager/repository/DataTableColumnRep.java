package com.wyk.dsmanager.repository;

import com.wyk.dsmanager.entity.DsDataTableColumn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/8
 * \*
 * \* Description
 *
 * @author wyk
 */
@Repository
public interface DataTableColumnRep extends JpaRepository<DsDataTableColumn, String> {
}
