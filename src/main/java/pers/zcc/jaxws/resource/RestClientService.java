package pers.zcc.jaxws.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterClientHeaders;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * RestClientService
 *
 * @author zhangchangchun
 * @date 2024/1/19
 * @since 1.0
 */
@RegisterRestClient // 注册远程服务
@RegisterClientHeaders(RestClientHeadersFactory.class) // 注册请求头处理器，为了将头部认证值透传到远程调用请求头上
@Path("/ws")
public interface RestClientService {

    @GET
    @Path("/reflection")
    @Produces(MediaType.APPLICATION_JSON)
    String reflection();
}
