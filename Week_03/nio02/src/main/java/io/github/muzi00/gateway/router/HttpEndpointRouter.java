package io.github.muzi00.gateway.router;

import java.util.List;

public interface HttpEndpointRouter {
    
    String route(List<String> endpoints);
    
}
