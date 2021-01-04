package com.xtt.transaction.configuration;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * GaywayConfiguration
 *
 * @author dexu.tian
 * @date 2020/12/31
 */
@Configuration
public class GatewayConfiguration {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("path_route", r -> r.path("/synctime/get")
                        .uri("http://localhost:8080/order-storesync/"))
                .build();
    }

}
