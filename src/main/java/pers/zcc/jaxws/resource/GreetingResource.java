package pers.zcc.jaxws.resource;

import io.quarkus.hibernate.orm.PersistenceUnit;
import io.smallrye.common.annotation.RunOnVirtualThread;
import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import pers.zcc.cdi.Translator;
import pers.zcc.config.GreetingConfig;
import pers.zcc.event.BusinessService;
import pers.zcc.orm.hibernate.entity.world.Country;
import pers.zcc.orm.mybatis.entity.City;
import pers.zcc.orm.mybatis.mapper.CityMapper;
import pers.zcc.reflection.TestReflection;

import java.security.Principal;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Path("/ws")
public class GreetingResource {

    //    @Inject //使用ConfigProperty注解可不需要注入注解
    @ConfigProperty(name = "quarkus.http.port", defaultValue = "8080")
    String httpPort;

    @Inject //注入批量配置
    GreetingConfig greetingConfig;

    @Inject //直接可以注入被produce声明的值
    public double pi;

    @Inject //直接可以注入被produce声明方法返回的值
    public List<String> names;

    @Inject
    Translator translator;

    @Inject
    @PersistenceUnit("world") // hibernate声明使用的持久化单元（数据源）
    EntityManager worldEntityManager;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Path("/hello")
    @PermitAll
    public String hello() {
        System.out.println(httpPort);
        System.out.println(ConfigProvider.getConfig().getConfigValue("quarkus.http.port"));
        System.out.println(greetingConfig.verb() + " " + greetingConfig.name() + " " + greetingConfig.to().who());
        return "Hello RESTEasy";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/cdi")
    @PermitAll
    public List<String> test(@QueryParam("p") String s) {
        System.out.println("param:" + s);
        System.out.println("consumer.pi:" + pi);
        System.out.println(translator.getSentence());
        return names;
    }

    @Inject
    BusinessService service;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/event")
    @PermitAll
    public String event(String s) {
        service.doSomething();
        return "ok";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/hibernate/{code}")
    @PermitAll
    //    @Transactional  // 框架内置事务管理器
    public Country hibernate(@PathParam("code") String code) {
        //        Country emr =
        //                worldEntityManager.createNamedQuery("Country.findByPrimaryCode",Country.class).setParameter
        //                (0, "CHN").getSingleResult();
        return Country.findByPrimaryCode(code);
    }

    @Inject
    CityMapper cityMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/mybatis/{code}")
    @PermitAll
    public City mybatis(@PathParam("code") String name) {
        return cityMapper.findByName(name);
    }

    @Inject
    TestReflection testReflection;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/reflection")
    @RolesAllowed({ "admin" })
    public String reflection() {
        return testReflection.getName();
    }

    /**
     * 也能通过编程获取自定义的restClient
     * restClientService = QuarkusRestClientBuilder.newBuilder()
     * .baseUri(URI.create("https://localhost:8081"))
     * .httpClientOptions(options)
     * .build(RestClientService.class);
     */
    @RestClient
    RestClientService restClientService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/restclient")
    @RolesAllowed({ "user", "admin" }) // 在任何基于注释的授权检查之前，将执行配置认证检查，因此必须通过两项检查才能允许请求。
    public Response restclient(@Context Request request, @Context SecurityContext securityContext) {
        Principal principal = securityContext.getUserPrincipal();// 这里获取的Principal就是jwt里的
        System.out.println(principal.getName());
        return Response.ok(restClientService.reflection()).build();
    }

    /**
     * @RunOnVirtualThread // 此注解会让方法运行在虚拟线程（virtual thread）上。
     * 在reactive 扩展下才会生效。reactive可以对每个请求使用一个虚拟线程来处理，底层却只使用有限数量的平台线程(platform thread)，从而大大提高服务器并发能力
     * 虚拟线程适用于io密集型任务（jvm遇到阻塞调用会自动切换虚拟线程任务，在io执行完毕后切回来），
     * 在cpu密集型任务上基本没用，反而可能阻塞承载线程，影响其它虚拟线程任务执行，或导致创建新的承载线程(carriage thread,执行虚拟线程所使用的平台线程)，
     * 进而导致内存使用过高（虚拟线程数量通常很多）。错误使用虚拟线程可能导致占用资源比直接使用平台线程还要高
     * 某些场景下虚拟线程甚至会形成pinning状态从而无法从承载的平台线程上卸载。JEP425：
     * 1.当在同步代码块或同步方法（synchronized修饰，使用reentrantLock可避免）内使用虚拟线程执行阻塞操作；
     * 2.当虚拟线程在本地方法或外部方法（foreign function）内执行阻塞操作时
     * 许多数据库驱动包、第三方库会造成这种问题
     * 虚拟线程禁止池化，复用.通常进程内可创建上百万个虚拟线程。虚拟线程的挂起和恢复是廉价的
     * 由于每个虚拟线程都会存储ThreadLocal里的内容，而通常会创建大量虚拟线程，这会造成巨大内存开销，因此对待thread local也要慎重
     * 现在的虚拟线程还不能随便使用，需要开发人员识别使用场景，正确使用它。因此quarkus限制了虚拟线程的使用
     */
    //    @RunOnVirtualThread
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/virtualThread")
    @PermitAll
    public Response blockingMethod() {
        //        try {
        //            System.out.println(
        //                    "blockingMethod tname::" + Thread.currentThread().getName() + "-" + Thread.currentThread()
        //                            .threadId());
        //            Thread.sleep(5000);
        //        } catch (InterruptedException e) {
        //            e.printStackTrace();
        //        }
        // 原生用法 在resteasy-reactive 扩展中以@RunOnVirtualThread标记的web接口会由虚拟线程执行
        Thread.ofVirtual().name("test-virtual").uncaughtExceptionHandler((t, e) -> {
            e.printStackTrace();
            System.out.println(t.getName());
        }).start(() -> {
            System.out.println(
                    "blockingMethod tname1::" + Thread.currentThread().getName() + "-" + Thread.currentThread()
                            .threadId());
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
            }
        });
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            executor.submit(() -> {
                System.out.println(
                        "blockingMethod tname2::" + Thread.currentThread().getName() + "-" + Thread.currentThread()
                                .threadId());
                System.out.println(11);
            });
        }
        return Response.ok().build();
    }

}
