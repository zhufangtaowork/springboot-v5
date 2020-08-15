package com.example.springbootv5.config;

import com.example.springbootv5.common.ResultView;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * @author fangtaozhu
 */
@Component
@Aspect
@Slf4j
public class HttpTimeAspect{
    /**
     * @Description：方法名
     **/
    String methodName;
    /**
     * @Description：类名
     **/
    String className;
    /**
     * @Description：开始时间
     **/
    long startTime;

     /**
     * 定义一个切入点.
     * 解释下：
     *
     * ~ 第一个 * 代表任意修饰符及任意返回值.
     * ~ 第二个 * 定义在web包或者子包
     * ~ 第三个 * 任意方法
     * ~ .. 匹配任意数量的参数.
     */
    @Pointcut("execution( public * com.example.springbootv5.controller..*.*(..))")
    public void aopPointCut() {
    }

    @Before("aopPointCut()")
    public void doBefore(JoinPoint joinPoint) {
        methodName = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        startTime = System.currentTimeMillis();
        log.info("{}--------------start",methodName);
    }

    @After("aopPointCut()")
    public void doAfter() {
        long E_time = System.currentTimeMillis() - startTime;
        log.info("执行 " + methodName + " 接口耗时为：" + E_time + "ms");
    }

}