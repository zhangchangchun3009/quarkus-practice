package pers.zcc.jaxws.resource;

import io.smallrye.jwt.build.Jwt;
import jakarta.ws.rs.core.MultivaluedHashMap;
import jakarta.ws.rs.core.MultivaluedMap;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.rest.client.ext.ClientHeadersFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.logging.Level;

/**
 * 对rest client的请求头进行自定义处理
 * RestClientHeadersFactory
 *
 * @author zhangchangchun
 * @date 2024/1/23
 * @since 1.0
 */
public class RestClientHeadersFactory implements ClientHeadersFactory {
    public static final String PROPAGATE_PROPERTY = "org.eclipse.microprofile.rest.client.propagateHeaders";

    @Override
    public MultivaluedMap<String, String> update(MultivaluedMap<String, String> incomingHeaders,
            MultivaluedMap<String, String> clientOutgoingHeaders) {
        MultivaluedMap<String, String> propagatedHeaders = new MultivaluedHashMap();
        Optional<String> propagateHeaderString = getHeadersProperty();
        if (propagateHeaderString.isPresent()) {
            Arrays.stream(((String) propagateHeaderString.get()).split(",")).forEach((header) -> {
                if (incomingHeaders.containsKey(header)) {
                    propagatedHeaders.put(header, incomingHeaders.get(header));
                }
            });
        }
        String jwt = getJwt();
        propagatedHeaders.put("Authorization", Collections.singletonList("Bearer " + jwt));
        System.out.println(propagatedHeaders);
        return propagatedHeaders;
    }

    /**
     * 根据远程服务要求获取认证token，可能是静态配置也可能通过接口调用得到
     *
     * @return
     */
    private static String getJwt() {
        String jwt = Jwt.subject("system").upn("system").groups(new HashSet<>(Arrays.asList("admin"))).sign();
        return jwt;
    }

    private static Optional<Config> config() {
        try {
            return Optional.ofNullable(ConfigProvider.getConfig());
        } catch (NoClassDefFoundError | IllegalStateException | ExceptionInInitializerError var1) {
            return Optional.empty();
        }
    }

    private static Optional<String> getHeadersProperty() {
        Optional<Config> c = config();
        return c.isPresent()
                ? Optional.ofNullable(
                ((Config) c.get()).getOptionalValue(PROPAGATE_PROPERTY, String.class).orElse((String) null))
                : Optional.empty();
    }
}
