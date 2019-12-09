package com.waiterlong.vipmis.component;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        Logger logger = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        long beginTime = System.currentTimeMillis();
        //类和方法名称
//        String classAndMethodName = joinPoint.getTarget().getClass().getSimpleName() + "." + joinPoint.getSignature().getName();
        logger.debug("{} begin, input parameter: {}", joinPoint.getSignature().getName(), JSON.toJSONString(filterParamList(joinPoint)));
        //响应结果
        Object res = joinPoint.proceed();
        long endTime = System.currentTimeMillis();
        logger.debug("{} end, spend time: {}ms, output parameter: {}", joinPoint.getSignature().getName(), endTime - beginTime, JSON.toJSONString(res));
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
