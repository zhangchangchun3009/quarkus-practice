package pers.zcc.jaxws.resource;

import io.smallrye.jwt.auth.principal.JWTParser;
import io.smallrye.jwt.auth.principal.ParseException;
import io.smallrye.jwt.build.Jwt;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.util.Arrays;
import java.util.HashSet;

/**
 * PublicService
 *
 * @author zhangchangchun
 * @date 2024/1/22
 * @since 1.0
 */
@Path("/public")
public class PublicService {
    @GET
    @Path("/login/{username}/{password}")
    @PermitAll
    /**
     * upn MicroProfile JWT RBAC spec规范指定的Principle名
     * groups里可以放角色等权限信息
     * claim可以放其它自定义信息，如加密的信息
     * */ public String login(@PathParam("username") String username, @PathParam("password") String password) {
        if ("aaa".equals(username) && "bbb".equals(password)) {
            return Jwt.subject(username).upn(username).groups(new HashSet<>(Arrays.asList("user", "admin"))).sign();
        } else if ("ccc".equals(username)) {
            return Jwt.subject(username).upn(username).groups(new HashSet<>(Arrays.asList("user")))
                    .claim("secret", "none").sign();
        } else {
            return "anonymous";
        }
    }

    @Inject
    JWTParser jwtParser;

    /**
     * 如果以请求头的Authorization: Bearer XXX携带token，则可以自动注入JsonWebToken对象
     */
    @GET
    @Path("/filter/{token}")
    @PermitAll
    public boolean filterApi(@PathParam("token") String token) {
        try {
            JsonWebToken jwt = jwtParser.parse(token); // 过期会抛出口令无效异常，这里不仅仅是做解码
            if (jwt.getClaimNames() == null) {
                return false;
            }
            System.out.println(jwt);
            System.out.println(jwt.getName());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
