package com.wyk.dsmanager.utils;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/7
 * \*
 * \* Description
 *
 * @author wyk
 */
public class Constants {
    public final static String ORACLE_MARK = "Oracle";
    public final static String ORACLE_DRIVER = "oracle.jdbc.driver.OracleDriver";
    public final static String ORACLE_LIST_TABLES = "select table_name from user_tab_comments where TABLE_TYPE = 'TABLE' order by table_name asc";
    public final static String ORACLE_LIST_TABLE_COLUMNS = "select t.column_name as columnName,t.data_type as dataType,c.comments as columnComment,c.comments as columnTitle,1 as visiable from user_tab_columns t left join  user_col_comments c on c.table_name = t.TABLE_NAME and c.column_name = t.COLUMN_NAME where t.Table_Name=";

    public final static String MYSQL_MARK = "Mysql";
    public final static String MYSQL_DRIVER = "com.mysql.jdbc.Driver";
    public final static String MYSQL_LIST_TABLES = "show tables";
    public final static String MYSQL_LIST_TABLE_COLUMNS = "select column_name as columnName,is_nullable as isNullable,data_type as dataType,column_key as columnKey,column_comment as columnComment,column_comment as columnTitle,1 as visiable from information_schema.COLUMNS where TABLE_NAME = ";

    public final static String PG_MARK = "PostgreSQL";
    public final static String PG_DRIVER = "";

    public final static String ES_MARK = "Elasticsearch";

}
