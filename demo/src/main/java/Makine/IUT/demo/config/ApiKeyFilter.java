package Makine.IUT.demo.config;

import Makine.IUT.demo.exception.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

    @Value("${api.key}")
    private String validApiKey;

    private static final String API_KEY_HEADER = "X-API-KEY";
    private static final List<String> SECURED_METHODS = Arrays.asList("POST", "PUT", "DELETE", "PATCH");

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String method = request.getMethod();
        String path = request.getRequestURI();

        // Skip API key validation for GET requests, H2 console, Swagger UI, and API docs
        if ("GET".equals(method) ||
            path.startsWith("/h2-console") ||
            path.startsWith("/swagger-ui") ||
            path.startsWith("/v3/api-docs") ||
            path.startsWith("/api-docs")) {
            filterChain.doFilter(request, response);
            return;
        }

        // Validate API key for secured methods
        if (SECURED_METHODS.contains(method)) {
            String apiKey = request.getHeader(API_KEY_HEADER);

            if (apiKey == null || !apiKey.equals(validApiKey)) {
                throw new UnauthorizedException("Invalid or missing API key");
            }
        }

        filterChain.doFilter(request, response);
    }
}
