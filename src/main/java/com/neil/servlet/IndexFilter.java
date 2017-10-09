package com.neil.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.DispatcherServlet;

@WebFilter(urlPatterns = "/*", filterName = "indexFilter")
public class IndexFilter implements Filter {
    Log log = LogFactory.getLog(IndexFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("init IndexFilter");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("doFilter IndexFilter");
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
