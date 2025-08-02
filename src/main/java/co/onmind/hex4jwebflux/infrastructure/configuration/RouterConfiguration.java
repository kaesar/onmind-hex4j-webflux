package co.onmind.hex4jwebflux.infrastructure.configuration;

import co.onmind.hex4jwebflux.infrastructure.handlers.RoleHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

/**
 * Configuration class for defining reactive routes using RouterFunction.
 * 
 * This configuration class sets up the routing for the role management API
 * using Spring WebFlux's functional reactive approach. It defines all the
 * HTTP endpoints and their corresponding handler methods in a declarative way.
 * 
 * <p>The router configuration follows RESTful conventions and provides a clean
 * separation between routing logic and request handling logic. All routes are
 * configured to work with JSON content type and follow reactive patterns.</p>
 * 
 * @author OnMind (Cesar Andres Arcila Buitrago)
 * @version 1.0.0
 * @since 1.0.0
 */
@Configuration
public class RouterConfiguration {
    
    /**
     * Defines the router function for role-related endpoints.
     * 
     * This method creates a RouterFunction that maps HTTP requests to handler methods
     * for role operations. The routes are organized following RESTful conventions:
     * 
     * <ul>
     *   <li>POST /api/v1/roles - Create a new role</li>
     *   <li>GET /api/v1/roles - Get all roles</li>
     *   <li>GET /api/v1/roles/{id} - Get a specific role by ID</li>
     *   <li>GET /api/v1/roles/search - Search roles by name pattern</li>
     * </ul>
     * 
     * All routes are configured to:
     * <ul>
     *   <li>Accept and produce JSON content type</li>
     *   <li>Use reactive processing with Mono/Flux</li>
     *   <li>Handle errors gracefully through the handler methods</li>
     *   <li>Follow hexagonal architecture principles</li>
     * </ul>
     * 
     * @param roleHandler the handler that processes role-related requests
     * @return a RouterFunction that defines all role-related routes
     */
    @Bean
    public RouterFunction<ServerResponse> roleRoutes(RoleHandler roleHandler) {
        return RouterFunctions
            .route(POST("/api/v1/roles")
                .and(accept(MediaType.APPLICATION_JSON))
                .and(contentType(MediaType.APPLICATION_JSON)), 
                roleHandler::createRole)
            .andRoute(GET("/api/v1/roles/search")
                .and(accept(MediaType.APPLICATION_JSON)), 
                roleHandler::searchRolesByName)
            .andRoute(GET("/api/v1/roles")
                .and(accept(MediaType.APPLICATION_JSON)), 
                roleHandler::getAllRoles)
            .andRoute(GET("/api/v1/roles/{id}")
                .and(accept(MediaType.APPLICATION_JSON)), 
                roleHandler::getRoleById);
    }
    
    /**
     * Defines additional router functions for API documentation and health checks.
     * 
     * This method creates supplementary routes that provide API information
     * and health check endpoints for monitoring and documentation purposes.
     * 
     * @return a RouterFunction that defines utility routes
     */
    @Bean
    public RouterFunction<ServerResponse> utilityRoutes() {
        return RouterFunctions
            .route(GET("/api/v1/health"), 
                request -> ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new HealthResponse("UP", "Role API is running")))
            .andRoute(GET("/api/v1/info"), 
                request -> ServerResponse.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(new ApiInfoResponse(
                        "Role Management API",
                        "1.0.0",
                        "Hexagonal Architecture with Spring WebFlux",
                        "/api/v1"
                    )));
    }
    
    /**
     * Record representing a health check response.
     * 
     * @param status the health status (UP/DOWN)
     * @param message additional health information
     */
    public record HealthResponse(
        String status,
        String message
    ) {}
    
    /**
     * Record representing API information response.
     * 
     * @param name the API name
     * @param version the API version
     * @param description the API description
     * @param basePath the API base path
     */
    public record ApiInfoResponse(
        String name,
        String version,
        String description,
        String basePath
    ) {}
}