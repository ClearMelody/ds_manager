package com.wyk.dsmanager.service.impl.baseDataSourceService;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.params.ExcelExportEntity;
import com.wyk.dsmanager.entity.DsDataTable;
import com.wyk.dsmanager.entity.DsDataTableColumn;
import com.wyk.dsmanager.entity.DsDatabase;
import com.wyk.dsmanager.entity.vo.DsDataTableColumnVo;
import com.wyk.dsmanager.repository.DataResourceRep;
import com.wyk.dsmanager.repository.DataTableColumnRep;
import com.wyk.dsmanager.repository.DataTableRep;
import com.wyk.dsmanager.repository.DatabaseRep;
import com.wyk.dsmanager.service.IBaseDataSourceService;
import com.wyk.dsmanager.service.impl.ExcelExportServerImpl;
import com.wyk.dsmanager.utils.ExcelDictHandle;
import com.wyk.dsmanager.utils.PageResult;
import com.wyk.dsmanager.utils.Result;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/7
 * \*
 * \* Description
 *
 * @author wyk
 */
@Service("baseDbSourceService")
public class BaseDbSourceServiceImpl extends BaseSourceService implements IBaseDataSourceService {
    @Resource(name = "dataTableRep")
    DataTableRep dataTableRep;
    @Resource(name = "databaseRep")
    DatabaseRep databaseRep;
    @Resource(name = "dataResourceRep")
    DataResourceRep dataResourceRep;
    @Resource(name = "dataTableColumnRep")
    DataTableColumnRep dataTableColumnRep;

    private static QueryRunner qr = new QueryRunner();

    @Override
    public Result listTables(String dbId) {
        DsDatabase entity = databaseRep.getOne(dbId);
        Connection conn = getConnection(entity);
        List<Object> result = null;
        try {
            result = qr.query(conn, getListTablesSql(entity.getDbType()), new ColumnListHandler<Object>());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Result.ok(result);
    }

    @Override
    public Result listTableColumns(String dbId, String dtName) {
        DsDatabase entity = databaseRep.getOne(dbId);
        Connection conn = getConnection(entity);
        List<DsDataTableColumnVo> result = null;
        try {
            result = qr.query(conn, getListTableColumnsSql(entity.getDbType()) + "'"+dtName+"'", new BeanListHandler<DsDataTableColumnVo>(DsDataTableColumnVo.class));
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Result.ok(result);
    }

    @Override
    public Result pageData(String tbId, String order, int page, int limit) {
        DsDataTable dataTable = dataTableRep.getOne(tbId);
        DsDatabase database = dataTable.getDsDatabase();
        Connection conn = getConnection(database);
        List<Map<String, Object>> result = null;
        long total = 0;
        try {
            String sql = getQuerySql(dataTable);
            String pageSql = getPageSql(database.getDbType(), sql.toString(), page, limit);
            result = qr.query(conn, pageSql, new MapListHandler());
            total = countSql(qr, conn, sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Map<String,Object> resultMap = new HashMap<>(2);
        resultMap.put("pageData", PageResult.setPageResult(PageRequest.of(page - 1, limit), total, result));
        resultMap.put("tableRows", getRowTitle(dataTable.getColumns()));
        return Result.ok(resultMap);
    }

    private List<Map<String,String>> getRowTitle(List<DsDataTableColumn> columns){
        List<Map<String,String>> rows = new ArrayList<>();
        for (DsDataTableColumn column : columns){
            if(column.getVisiable() == 1){
                Map<String,String> row = new HashMap<>(3);
                row.put("label", column.getColumnTitle());
                row.put("prop", column.getColumnName());
                row.put("dictId", column.getDictId());
                row.put("id", column.getId());
                rows.add(row);
            }
        }
        return rows;
    }

    private String getQuerySql(DsDataTable dataTable){
        List<DsDataTableColumn> columns = dataTable.getColumns();
        DsDatabase database = dataTable.getDsDatabase();
        StringBuilder sb = new StringBuilder("select ");
        sb.append(getColumnSql(columns));
        sb.append(" from ");
        sb.append(dataTable.getDtName());
        return sb.toString();
    }

    private String getColumnSql(List<DsDataTableColumn> columns){
        StringBuilder sb = new StringBuilder();
        for (DsDataTableColumn column : columns){
            if(column.getVisiable() == 1){
                sb.append(column.getColumnName());
                sb.append(",");
            }
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    @Override
    public Result showTableConstructure() {
        return null;
    }

    @Override
    public Result deleteTable() {
        return null;
    }

    @Override
    public Result createTable(String dbId, String sql) {
        DsDatabase entity = databaseRep.getOne(dbId);
        Connection conn = getConnection(entity);
        try {
            qr.execute(conn, sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return Result.ok();
    }

    @Override
    public void exportTableData(String tbId, Map<String,Object> param, HttpServletRequest request, HttpServletResponse response) {
        DsDataTable dataTable = dataTableRep.getOne(tbId);
        DsDatabase database = dataTable.getDsDatabase();
        Connection conn = getConnection(database);
        List<Map<String, Object>> result = null;
        long total = 0;
        int pageSize = 1000;
        Workbook workbook = null;
        try {
            request.getSession().setAttribute("percent", 0d);
            List<Map<String,String>> tableRows = getRowTitle(dataTable.getColumns());
            List<ExcelExportEntity> excelRows = new ArrayList<ExcelExportEntity>();
            for(Map<String,String> row : tableRows){
                ExcelExportEntity excelEntity = new ExcelExportEntity(row.get("label"), row.get("prop"));
                if(StringUtils.isNotBlank(row.get("dictId"))){
                    excelEntity.setDict(row.get("dictId"));
                }
                excelRows.add(excelEntity);
            }
            ExportParams exportParams = new ExportParams(null, dataTable.getDtNameCn());
            exportParams.setDictHandler(new ExcelDictHandle());

            String sql = getQuerySql(dataTable);
            param.put("sql", sql);
            param.put("dbType", database.getDbType());
            param.put("qr", qr);
            param.put("conn", conn);
            param.put("request", request);

            workbook = ExcelExportUtil.exportBigExcel(exportParams, excelRows, new ExcelExportServerImpl(), param);


        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            downLoadExcel(dataTable.getDtNameCn(), response, workbook);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 下载
     *
     * @param fileName 文件名称
     * @param response
     * @param workbook excel数据
     */
    private static void downLoadExcel(String fileName, HttpServletResponse response, Workbook workbook) throws IOException {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName + "." + ExcelTypeEnum.XLSX.getValue(), "UTF-8"));
            OutputStream os = response.getOutputStream();
            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public Result importTableData() {
        return null;
    }
}
