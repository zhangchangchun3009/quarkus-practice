package pers.zcc.launch;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;
import io.quarkus.runtime.annotations.CommandLineArguments;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.Arrays;

/**
 * AppLife
 *
 * @author zhangchangchun
 * @date 2024/1/11
 * @since 1.0
 */
@ApplicationScoped
public class AppLifecycleBean {

    private static final Logger LOGGER = Logger.getLogger("ListenerBean");

    @Inject
    @CommandLineArguments
    //可以在任何地方注入命令行参数。命令行参数如mvn quarkus:dev -Dquarkus.args="a,b";
    // 打包本地镜像后直接在命令后接参数，空格分隔。如.\quarkus-practice-1.0.0-SNAPSHOT-runner.exe a b
    String[] args;

    void onStart(@Observes StartupEvent ev) {
        LOGGER.info("参数个数："+args.length+",值: "+ Arrays.deepToString(args));
        LOGGER.info("The application is starting...");
    }

    void onStop(@Observes ShutdownEvent ev) {
        LOGGER.info("The application is stopping...");
    }

}
