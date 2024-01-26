package pers.zcc.reflection;

import jakarta.enterprise.context.ApplicationScoped;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * TestReflection
 *
 * @author zhangchangchun
 * @date 2024/1/17
 * @since 1.0
 */
@ApplicationScoped
public class TestReflection
{
    public String getName(){
        Foo foo = new Foo();
        foo.setName("aaa");
        try {
           Method m = foo.getClass().getDeclaredMethod("getName");
           Object name = m.invoke(foo);
           return (String) name;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
