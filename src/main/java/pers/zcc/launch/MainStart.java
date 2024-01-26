package pers.zcc.launch;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

import java.util.Arrays;

/**
 * MainStart
 * 当项目没有定义main方法时框架会自己生成一个启动类，也可以打包和通过命令行（quarkus cli或mvn）启动
 * 不要在main里写业务逻辑，因为此时上下文还没初始化
 * @author zhangchangchun
 * @date 2024/1/11
 * @since 1.0
 */
@QuarkusMain
public class MainStart {

    public static void main(String[] args) {
        System.out.println(Arrays.deepToString(args));
        Quarkus.run(MyApp.class,args);
    }

    public  static  class  MyApp implements QuarkusApplication {

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
