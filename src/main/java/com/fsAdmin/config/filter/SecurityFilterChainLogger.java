package com.fsAdmin.config.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class SecurityFilterChainLogger extends OncePerRequestFilter {

    private final FilterChainProxy filterChainProxy;

    public SecurityFilterChainLogger(FilterChainProxy filterChainProxy) {
        this.filterChainProxy = filterChainProxy;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        System.out.println("Request URI: " + request.getRequestURI());
        System.out.println("Security Filter Chain:");

        List<SecurityFilterChain> securityFilterChains = filterChainProxy.getFilterChains();
        for (SecurityFilterChain securityFilterChain : securityFilterChains) {
            if (securityFilterChain.matches(request)) { // 只打印匹配当前请求的过滤器链
                securityFilterChain.getFilters().forEach(filter -> {
                    System.out.println(filter.getClass().getName());
                });
                break; // 找到匹配的过滤器链后就不再继续
            }
        }

        System.out.println();
        chain.doFilter(request, response);
    }
}
