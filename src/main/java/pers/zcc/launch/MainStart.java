package pers.zcc.launch;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import java.util.Arrays;

/**
 * <p>quarkus框架，一个超音速亚原子的为了云原生时代开发产生的java应用框架（主要应用在web微服务开发，遵循eclipse microprofile微服务协议）
 * <p>使用graalvm获取native镜像能力，从而大大提高了应用启动速度，减少cpu、内存等资源占用。web基于vert.x，可以同时支持命令式和响应式编程
 *
 * <p>设计原则是容器优先（启动快，资源占用小，紧贴Kubernetes和docker），使用了编译期预执行（Build Time Processing）一些方法（初始化方法，评估静态属性）、
 * 避免反射调用（Reduction in Reflection Usage，编译器字节码技术改写反射代码，将动态代理使用静态代理改写等）、
 * 设计之初的grralvm本地镜像打包支持（First-Class Support for GraalVM Native Images，native编译器使用激进的死码消除技术，减少不必要的代码和类、配置）、
 * 本地镜像预启动（Native Image Pre-Boot，预执行许多框架的初始化代码并将结果序列化到镜像中，这样镜像运行时许多代码其实已经运行过了）
 * 紧贴Kubernetes（Kubernetes, but also bare metal），同时也支持物理机加速（aot比传统vm就是快）
 *
 * <p>MainStart
 * 当项目没有定义main方法时框架会自己生成一个启动类，也可以打包和通过命令行（quarkus cli或mvn）启动
 * <p>不要在main里写业务逻辑，因为此时上下文还没初始化
 *
 * @author zhangchangchun
 * @date 2024/1/11
 * @since 1.0
 */
@QuarkusMain
public class MainStart {

    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(args));
        Quarkus.run(MyApp.class, args);
    }

    public static class MyApp implements QuarkusApplication {

        @Override
        //此方法会在上下文初始化后被调用，业务逻辑应该写在这里，比如业务初始化和清理工作
        public int run(String... args) throws Exception {
            //Quarkus上下文启动完毕
            //...添加启动项
            Quarkus.waitForExit(); //阻塞方法，等待外部终止命令（ctr+C）或调用了Quarkus.asyncExit()
            //...添加退出前清理项
            return 0; //应用退出
        }
    }
}
