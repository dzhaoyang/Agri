package com.sunsea.parkinghere.framework.spring.mvc;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ErrorHandlerFilter implements Filter {
    
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain filterChain) throws IOException,
                                                 ServletException {
        try {
            filterChain.doFilter(request, response);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            request.setAttribute("exception", ex);
            request.getRequestDispatcher("/WEB-INF/pages/error.jsp")
                   .forward(request, response);
        }
    }
    
    public void destroy() {
    }
    
    // ...
}
