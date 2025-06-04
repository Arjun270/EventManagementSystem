package com.ems.Gateway.Filter;

import com.ems.Gateway.Utility.JwtValidator;
import com.google.common.net.HttpHeaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Autowired
    private JwtValidator jwtValidator;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            logger.debug("Processing request: {} {}", request.getMethod(), request.getURI());

            String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            logger.debug("Authorization header: {}", authHeader);

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                logger.error("Missing or invalid Authorization header");
                return handleUnauthorized(exchange.getResponse(), "Missing or invalid Authorization header");
            }

            String token = authHeader.substring(7);
            logger.debug("Extracted token: {}", token);

            if (!jwtValidator.validateToken(token)) {
                logger.error("Token validation failed");
                return handleUnauthorized(exchange.getResponse(), "Invalid or expired token");
            }

            String email = jwtValidator.getEmail(token);
            String role = jwtValidator.getClaim(token, "role");
            logger.debug("Token validated. Email: {}, Role: {}", email, role);

            request = request.mutate()
                    .header("X-User-Email", email != null ? email : "")
                    .header("X-User-Role", role != null ? role : "")
                    .build();
            logger.debug("Added custom headers to request");

            return chain.filter(exchange.mutate().request(request).build());
        };
    }

    private Mono<Void> handleUnauthorized(ServerHttpResponse response, String message) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String body = String.format("{\"error\": \"%s\"}", message);
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes());
        return response.writeWith(Mono.just(buffer));
    }

    public static class Config {
        // Configuration properties if needed
    }
}