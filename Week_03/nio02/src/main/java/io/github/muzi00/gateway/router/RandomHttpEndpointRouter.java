package io.github.muzi00.gateway.router;

import java.util.List;
import java.util.Random;

/**
 * 随机HttpEndpointRouter
 */
public class RandomHttpEndpointRouter implements HttpEndpointRouter {
    @Override
    public String route(List<String> endpoints) {
        if (null != endpoints && endpoints.size() > 0)
            return endpoints.get(new Random().nextInt(endpoints.size()));
        throw new RuntimeException("endpoints is null");
    }
}
