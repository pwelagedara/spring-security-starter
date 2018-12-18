package com.example.demo.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by pubudu welagedara on 12/17/18.
 */
public class ApiKeyAuthenticationFilter extends GenericFilterBean {

    private AuthenticationManager authenticationManager;

    public ApiKeyAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest)servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse)servletResponse;

        Optional<String> apiKey = Optional.ofNullable(httpServletRequest.getHeader("X-Api-Key"));

        if(apiKey.isPresent()) {
            ApiKeyAuthenticationToken apiKeyAuthenticationToken = new ApiKeyAuthenticationToken(apiKey.get());
            SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(apiKeyAuthenticationToken));
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
