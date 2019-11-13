package com.leverx.servletapp.user.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebFilter(filterName = "UserServletFilter", urlPatterns = {"/users", "/users/*"})
public class UserServletFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServletFilter.class.getSimpleName());

    private static final String CONTENT_TYPE = "application/json";

    @Override
    public void init(FilterConfig filterConfig) {
        LOGGER.info("Filter initialized.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
        servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());
        servletResponse.setContentType(CONTENT_TYPE);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        LOGGER.info("Filter destroyed.");
    }
}
