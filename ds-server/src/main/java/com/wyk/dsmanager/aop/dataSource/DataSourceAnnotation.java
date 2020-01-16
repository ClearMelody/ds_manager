package com.wyk.dsmanager.aop.dataSource;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * \* Created with IntelliJ IDEA
 * \* Date 2020/1/6
 * \*
 * \* Description
 *
 * @author wyk
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DataSourceAnnotation {

}
