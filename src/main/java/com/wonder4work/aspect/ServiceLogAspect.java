package com.wonder4work.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @since 1.0.0 2020/3/29
 *
 * 切面 记录Service 执行日志
 */
@Slf4j
@Component
@Aspect
public class ServiceLogAspect {

    /**
     * 切面表达式
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.wonder4work.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws Throwable {

        log.info("===== 开始执行 {}.{} =====",
                joinPoint.getTarget().getClass(),
                joinPoint.getSignature().getName());

        // 开始时间
        long begin = System.currentTimeMillis();

        // 执行
        Object result = joinPoint.proceed();

        long end = System.currentTimeMillis();

        // 时间差
        long takeTime = end - begin;

        if (takeTime > 3000) {
            log.error("===== 执行结束，耗时:{} 毫秒 =====", takeTime);
        } else if (takeTime > 2000) {
            log.warn("===== 执行结束，耗时:{} 毫秒 =====", takeTime);
        }else {
            log.info("===== 执行结束，耗时:{} 毫秒 =====", takeTime);

        }

        return result;
    }


}
