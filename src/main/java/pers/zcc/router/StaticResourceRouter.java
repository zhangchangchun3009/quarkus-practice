package pers.zcc.router;

import io.quarkus.runtime.StartupEvent;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

/**
 * quarkus web框架使用的vert.x，无servlet的web容器，路由请求都是使用router，配置路由路径，绑定handler
 * StaticResourceRouter
 *
 * @author zhangchangchun
 * @date 2024/1/29
 * @since 1.0
 */
@ApplicationScoped
public class StaticResourceRouter {

    void installRoute(@Observes StartupEvent startupEvent, Router router) {
        router.route().path("/static/*").handler(StaticHandler.create("static/"));
    }
}
