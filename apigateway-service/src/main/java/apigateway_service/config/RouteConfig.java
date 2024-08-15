package apigateway_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;

@Configuration
public class RouteConfig {

    @Bean
    public RouteLocator gatewayRouterFunctions(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("user-service", r -> r.path("/user-service/**")
                .uri("lb://USER-SERVICE"))

            .route("product-service", r -> r.path("/product-service/**")
                .uri("lb://PRODUCT-SERVICE"))

            .route("order-service", r -> r.path("/order-service/**")
                .uri("lb://ORDER-SERVICE"))

            .route("notification-service", r -> r.path("/notification-service/**")
                .uri("lb://NOTIFICATION-SERVICE"))

            .route("board-service", r -> r.path("/board-service/**")
                .uri("lb://BOARD-SERVICE"))

            .route("basket-service", r -> r.path("/basket-service/**")
                .uri("lb://BASKET-SERVICE"))

            .build();
    }
}
