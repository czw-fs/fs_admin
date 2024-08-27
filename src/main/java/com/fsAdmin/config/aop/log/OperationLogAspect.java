package com.fsAdmin.config.aop.log;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class OperationLogAspect {


    @Pointcut("@annotation(com.fsAdmin.config.aop.log.OperationLog)")
    public void pointCut() {}

    @Around("pointCut()")
    public void afterLogWrite(JoinPoint joinPoint) {
        OperationLog operationLog = getLog(joinPoint);
        if (operationLog == null) {
            return;
        }
//        insert
    }

    private OperationLog getLog(JoinPoint joinPoint) {

        return null;
    }
}
