package com.fsAdmin.config.aop.log;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface OperationLog {

    //操作名称
    String operationName() default "";

    //操作类型
    String operationType() default "";
}
