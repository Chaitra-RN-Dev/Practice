package com.example.apigateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

@Slf4j
@Component
public class RequestLoggingFilter implements GlobalFilter, Ordered {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange,
                             org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();

        log.info("Incoming Request Path: {}", path);

        return chain.filter(exchange)
                .then(Mono.fromRunnable(() ->
                        log.info("Response completed for path: {}", path)
                ));
    }

    @Override
    public int getOrder() {
        return 1;
    }
}