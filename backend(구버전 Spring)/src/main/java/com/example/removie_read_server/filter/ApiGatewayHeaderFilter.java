package com.example.removie_read_server.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ApiGatewayHeaderFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(ApiGatewayHeaderFilter.class);

    @Value("${api.gateway.secret}")
    private String headerSecret;

    @Value("${api.gateway.header}")
    private String headerName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String secretHeader = request.getHeader(headerName);

        if (secretHeader == null || !secretHeader.equals(headerSecret)) {
            logger.info("Invalid API Gateway header: {}", secretHeader);
            response.sendError(HttpStatus.NOT_FOUND.value());
            return;
        }

        filterChain.doFilter(request, response);
    }
}