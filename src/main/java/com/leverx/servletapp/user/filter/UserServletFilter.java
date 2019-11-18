package com.leverx.servletapp.user.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

public class UserServletFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserServletFilter.class.getSimpleName());

    @Override
    public void init(FilterConfig filterConfig) {
        LOGGER.info("Filter initialized.");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        servletRequest.setCharacterEncoding(UTF_8.name());
        servletResponse.setCharacterEncoding(UTF_8.name());
        servletResponse.setContentType(APPLICATION_JSON);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        LOGGER.info("Filter destroyed.");
    }
}
