package pers.zcc.cdi;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

/**
 * Producer
 *
 * @author zhangchangchun
 * @date 2024/1/11
 * @since 1.0
 */
@ApplicationScoped
public class Producer {
    //quarkus的bean一般不使用private属性。1.graalvm的一个限制是使用反射必需声明，且打的本地镜像体积会变大；
    //2.框架访问私有属性需要使用反射，
    //总的来说在系统中尽量避免使用反射
    @Produces
    public double pi = Math.PI;

    @Produces //基础类型不能设置为normal scope【@ApplicationScoped, @RequestScoped, etc.】，
    //可以指定为 pseudo-scope (@Dependent and @Singleton )
    public int a=1;

    @Produces
    public String b="sentence";

    @Produces // 不声明scope默认是dependent多实例
    public List<String> names(double pi) {
        System.out.println(pi);
        List<String> names = new ArrayList<>();
        names.add("Andy");
        names.add("Adalbert");
        names.add("Joachim");
        return names;
    }

    @ApplicationScoped
    public Boolean produceTrue(){
        return Boolean.TRUE;
    }

}
