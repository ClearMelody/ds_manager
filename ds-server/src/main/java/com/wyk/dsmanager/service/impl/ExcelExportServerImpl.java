package com.wyk.dsmanager.service.impl;

import cn.afterturn.easypoi.handler.inter.IExcelExportServer;
import com.wyk.dsmanager.service.impl.baseDataSourceService.BaseSourceService;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/15
 * \*
 * \* Description
 *
 * @author wyk
 */
public class ExcelExportServerImpl extends BaseSourceService implements IExcelExportServer {
    @Override
    public List<Object> selectListForExcelExport(Object o, int i) {
        Map<String, Object> param = (Map<String, Object>) o;
        String sql = (String) param.get("sql");
        String dbType = (String) param.get("dbType");
        Integer pageSize = 1000;
        QueryRunner qr = (QueryRunner) param.get("qr");
        Connection conn = (Connection) param.get("conn");
        HttpServletRequest request = (HttpServletRequest) param.get("request");
        try {
            long total = countSql(qr, conn, sql);
            int totalPage = (int) (total / pageSize + 1);
            Object percentSession = request.getSession().getAttribute("percent");
            Double percent = 0d;
            if(percentSession != null){
                percent = (Double) percentSession;
            }
            if (i <= totalPage) {
                String pageSql = getPageSql(dbType, sql.toString(), i, pageSize);
                List<Map<String,Object>> result = qr.query(conn, pageSql, new MapListHandler());

                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.setMaximumFractionDigits(2);
                if (i != totalPage) {
                    percent += Double.parseDouble(numberFormat.format((float) pageSize / (float) total * 100));
                } else {
                    percent += Double.parseDouble(numberFormat.format((float) result.size() / (float) total * 100));
                }
                if(percent > 100){
                    percent = 100d;
                }
                request.getSession().setAttribute("percent", percent);
                List<Object> list = new ArrayList<>();
                for (Map<String,Object> map : result){
                    list.add(map);
                }
                return list;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
