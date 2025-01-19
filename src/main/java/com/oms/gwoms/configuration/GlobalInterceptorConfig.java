package com.oms.gwoms.configuration;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class GlobalInterceptorConfig implements GlobalFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        HttpHeaders headers = request.getHeaders();
        URI uri = request.getURI();
        String token = headers.getFirst(HttpHeaders.AUTHORIZATION);

        log.info("uri: {}", uri);
        if (!userHasPermission(token) && !isExcluded(uri)) {
            exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    private boolean isExcluded(URI uri) {
//        String path = uri.getPath();
//        if (path != null) {
//            List<String> excludeds = Arrays.asList(
//                    "/msauth/login",
//                    "/msauth/logout",
//                    "/msauth/refresh");
//            for (String excluded : excludeds) {
//                if (path.contains(excluded)) {
//                    return true;
//                }
//            }
//        }
//        return false;
        return true;
    }

    private boolean userHasPermission(String token) {
        // tokenService.isValid(token);
        return true;
    }
}
