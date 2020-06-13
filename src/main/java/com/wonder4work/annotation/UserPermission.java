package com.wonder4work.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解 用于配置方法权限名与数据库的permission的url对应
 * 没有使用的地方表示默认权限存在
 * @since 1.0.0 2020/6/12
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UserPermission {

    String value() default "";

}
