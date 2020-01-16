package com.wyk.dsmanager.service.impl.baseDataSourceService;

import com.wyk.dsmanager.entity.DsDatabase;
import com.wyk.dsmanager.utils.Constants;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/7
 * \*
 * \* Description
 *
 * @author wyk
 */
public class BaseSourceService {
    public Connection getConnection(DsDatabase entity) {
        try {
            Class.forName(getDriverName(entity.getDbType()));
            return DriverManager.getConnection(entity.getDbUrl(), entity.getDbUsername(), entity.getDbPassword());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDriverName(String dbType){
        switch (dbType){
            case Constants.ORACLE_MARK:
                return Constants.ORACLE_DRIVER;
            case Constants.MYSQL_MARK:
                return Constants.MYSQL_DRIVER;
            case Constants.PG_MARK:
                return Constants.PG_DRIVER;
            default:
                return null;
        }
    }

    public String getListTablesSql(String dbType){
        switch (dbType){
            case Constants.ORACLE_MARK:
                return Constants.ORACLE_LIST_TABLES;
            case Constants.MYSQL_MARK:
                return Constants.MYSQL_LIST_TABLES;
            default:
                return null;
        }
    }

    public String getListTableColumnsSql(String dbType){
        switch (dbType){
            case Constants.ORACLE_MARK:
                return Constants.ORACLE_LIST_TABLE_COLUMNS;
            case Constants.MYSQL_MARK:
                return Constants.MYSQL_LIST_TABLE_COLUMNS;
            default:
                return null;
        }
    }

    public String getPageSql(String dbType, String sql, int page, int limit){
        switch (dbType){
            case Constants.ORACLE_MARK:
                return "select * from (select t.*,rownum as num from (" + sql + ") t where rownum <= " + page*limit + ") where num >= " + (page - 1)*limit;
            case Constants.MYSQL_MARK:
                return "select * from (" + sql + ") t1 limit " + (page - 1)*limit + "," +limit;
            default:
                return null;
        }
    }

    public long countSql(QueryRunner qr, Connection conn, String sql) throws SQLException {
        long total = 0;
        Object count = qr.query(conn, "select count(*) from (" + sql + ") t2", new ScalarHandler<Object>());
        if(count instanceof BigDecimal){
            total = ((BigDecimal) count).longValue();
        }else{
            total = (long) count;
        }
        return total;
    }

    /**
     * Excel 类型枚举
     */
    enum ExcelTypeEnum {
        XLS("xls"), XLSX("xlsx");
        private String value;

        ExcelTypeEnum(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
