package com.wyk.dsmanager.controller;

import com.google.common.collect.Maps;
import com.wyk.dsmanager.aop.dataSource.DataSourceAnnotation;
import com.wyk.dsmanager.service.IBaseDataSourceService;
import com.wyk.dsmanager.utils.Result;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ArrayHandler;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/6
 * \*
 * \* Description
 *
 * @author wyk
 */
@RestController
@RequestMapping("/api/base-db")
public class BaseDataSourceController {

    @Resource(name = "baseDbSourceService")
    IBaseDataSourceService baseDbSourceService;

    @GetMapping("/list-tables/{dbId}")
    public Result listTables(@PathVariable(value = "dbId") String dbId){ return baseDbSourceService.listTables(dbId); }

    @GetMapping("/list-columns")
    public Result listTables(@RequestParam(value = "dbId") String dbId,
                             @RequestParam(value="dtName") String dtName){
        return baseDbSourceService.listTableColumns(dbId, dtName);
    }

    @GetMapping("/page-data")
    public Result pageData(@RequestParam(value = "tbId") String tbId,
                           @RequestParam(value="page", defaultValue = "1") Integer page,
                           @RequestParam(value="limit", defaultValue = "10") Integer limit,
                           @RequestParam(value="order", defaultValue = "") String order){
        return baseDbSourceService.pageData(tbId, order, page, limit);
    }

    @PostMapping("/tb-create")
    public Result tbCreate(@RequestBody Map<String,Object> params){
        String dbId = (String) params.get("dbId");
        String sql = (String) params.get("sql");
        return baseDbSourceService.createTable(dbId, sql);
    }
}
