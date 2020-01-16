package com.wyk.dsmanager.service;

import com.wyk.dsmanager.utils.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.print.Pageable;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/7
 * \*
 * \* Description
 *
 * @author wyk
 */
public interface IBaseDataSourceService {
    Result listTables(String dbId);
    Result listTableColumns(String dbId, String dtName);
    Result pageData(String tbId, String order, int page, int limit);
    Result showTableConstructure();
    Result deleteTable();
    Result createTable(String dbId, String sql);
    void exportTableData(String tbId, Map<String,Object> param, HttpServletRequest request, HttpServletResponse response);
    Result importTableData();
}
