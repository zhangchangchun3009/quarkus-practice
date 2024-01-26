package pers.zcc.jaxws.resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import pers.zcc.jakarta.interceptor.Logged;

/**
 * MyService
 *
 * @author zhangchangchun
 * @date 2024/1/11
 * @since 1.0
 */
@ApplicationScoped
@Path("/interceptorTest")
@Produces(MediaType.APPLICATION_JSON)
public class MyService {

    @Logged
    @GET
    public String doSomething(){return "MyService doSomething";}

}
