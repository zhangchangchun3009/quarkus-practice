package pers.zcc.launch;

import io.quarkus.runtime.Shutdown;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * ShutdownBean
 *
 * @author zhangchangchun
 * @date 2024/1/12
 * @since 1.0
 */
@ApplicationScoped
public class ShutdownBean {

    @Shutdown //非私有非静态无参
    void shutdown() {
        // place the logic here
    }
}
