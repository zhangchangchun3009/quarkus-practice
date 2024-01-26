package pers.zcc.reflection;

import io.quarkus.runtime.annotations.RegisterForReflection;

/**
 * Foo
 *
 * @author zhangchangchun
 * @date 2024/1/17
 * @since 1.0
 */
/**
 * @RegisterForReflection 使用注解的方式声明有反射调用，这样在编译成本地镜像后才能正常调用。另一种方式是在reflection-config.json中声明
 * 如果你的类在第三方的jar中，你可以通过使用一个空的类来做，这个空的类将为它托管 @RegisterForReflection 。
 * 如@RegisterForReflection(targets={ MyClassRequiringReflection.class, MySecondClassRequiringReflection.class})
 * public class MyReflectionConfiguration {
 * }
 * JSON库通常使用反射来将对象序列化为JSON，所以被序列化的类一般都要加这个注解
*/
public class Foo {

    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
