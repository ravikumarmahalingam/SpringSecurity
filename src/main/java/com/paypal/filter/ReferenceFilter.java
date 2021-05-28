package com.paypal.filter;

import javax.servlet.*;
import java.io.IOException;

//This class is created to add a new filter in the filter chain of the spring framework.
//The new filter also has to be added in the securityConfig file in order to tell the framework.
public class ReferenceFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
