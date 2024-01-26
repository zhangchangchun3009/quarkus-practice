package pers.zcc.jakarta.interceptor;

import jakarta.annotation.Priority;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * LogInterceptor 类似切面编程
 *
 * @author zhangchangchun
 * @date 2024/1/11
 * @since 1.0
 */
@Logged //绑定拦截点
@Interceptor //注册为拦截器,同时也注册成了dependent类型的bean
@Priority(1)//数字越小在拦截链中越靠前
public class LogInterceptor {

    AtomicInteger cnt = new AtomicInteger(0);

    @AroundInvoke
    Object logInvocation(InvocationContext context) {
        // ...log before
        Object ret = null;
        try {
            System.out.println("prams: "+Arrays.deepToString(context.getParameters()));
            cnt.incrementAndGet();
            ret = context.proceed();//如果有其它拦截器进入下环节，否则执行方法
            System.out.println("ret： "+ret);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        // ...log after
        return ret;
    }
}
