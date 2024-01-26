package pers.zcc.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "test.greeting") //配置前置节点
public interface GreetingConfig {
    @WithName("message")  //方法名映射到配置节点名，默认按方法名搜索节点
    String verb();

    @WithDefault("China") //缺少节点时使用默认值
    String name();

    To to(); //嵌套对象节点

   public interface To{
        String who();
    }

}