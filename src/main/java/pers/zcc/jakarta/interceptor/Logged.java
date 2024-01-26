package pers.zcc.jakarta.interceptor;

import jakarta.interceptor.InterceptorBinding;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Logged
 *
 * @author zhangchangchun
 * @description TODO
 * @date 2024/1/11
 * @since 1.0
 */
@InterceptorBinding //表明这是一个绑定拦截器和拦截点的注解
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE,ElementType.CONSTRUCTOR})
@Inherited
public @interface Logged {
}
