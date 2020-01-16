package com.wyk.dsmanager.aop.dataSource;

import com.google.common.collect.Maps;
import com.wyk.dsmanager.entity.DsDatabase;
import com.wyk.dsmanager.repository.DatabaseRep;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/6
 * \*
 * \* Description
 *
 * @author wyk
 */
@Aspect
@Service
public class DataSourceAop {
    @Resource(name = "databaseRep")
    DatabaseRep databaseRep;


    @Around(value = "@annotation(com.wyk.dsmanager.aop.dataSource.DataSourceAnnotation)")
    public Object  pointCut(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        DataSourceAnnotation dataSourceAnnotation = methodSignature.getMethod().getAnnotation(DataSourceAnnotation.class);
        Object[] args = joinPoint.getArgs();
        HttpServletRequest request = this.getArg(args, HttpServletRequest.class);

        DsDatabase entity = databaseRep.getOne(request.getParameter("resId"));
        Connection conn = this.getArg(args, Connection.class);
        Class.forName("com.mysql.jdbc.Driver");
        String url = entity.getDbUrl();
        conn = DriverManager.getConnection(url, entity.getDbUsername(), entity.getDbPassword());
        return joinPoint.proceed();

    }

    private <T> T getArg(Object[] args, Class<T> clz){
        for(Object o : args){
            if(clz.isInstance(o)){
                return (T)o;
            }
        }
        return null;
    }
}
