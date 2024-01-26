package pers.zcc.launch;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * StartupBean
 *
 * @author zhangchangchun
 * @date 2024/1/11
 * @since 1.0
 */
@ApplicationScoped
public class StartupBean {

    @Startup //方法必需无参、非静态、没有Produces注解
    void init(){

    }
}
