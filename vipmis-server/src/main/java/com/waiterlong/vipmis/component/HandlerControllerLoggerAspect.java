package com.waiterlong.vipmis.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* Date: 2019/12/9
 * \*
 * \* Description:
 * \
 *
 * @author walter_long
 */
@Slf4j
@Aspect
@Component
public class HandlerControllerLoggerAspect {
    /**
     * 切面处理方法
     *
     * @param joinPoint 流程切面点
     * @return 响应结果
     */
    @Around("execution(* com.waiterlong.vipmis.controller.*.*.*(..))")
    public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
        //类和方法名称
        String classAndMethodName = joinPoint.getTarget().getClass().getSimpleName() + "." + joinPoint.getSignature().getName();
        log.debug("Controller Execute：{},input parameter：{}", classAndMethodName, JSONArray.toJSONString(filterParamList(joinPoint)));
        //响应结果
        Object res = joinPoint.proceed();
        log.debug("Controller Execute：{},output parameter：{}", classAndMethodName, JSON.toJSONString(res));
        return res;
    }

    /**
     * 过滤参数列表
     *
     * @param joinPoint
     * @return
     */
    private List<Object> filterParamList(ProceedingJoinPoint joinPoint) {
        List<Object> paramsList = new ArrayList<>();
        Object[] arrays = joinPoint.getArgs();
        for (int i = 0; i < arrays.length; i++) {
            if (!(arrays[i] instanceof HttpServletRequest)) {
                paramsList.add(arrays[i]);
            }
        }
        return paramsList;
    }
}
