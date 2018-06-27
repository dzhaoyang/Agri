package com.sunsea.parkinghere.framework.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunsea.parkinghere.framework.web.WebContextHolder;

public class WebContextFilter implements Filter {
    
    public void init(FilterConfig arg0) throws ServletException {
    }
    
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException,
                                           ServletException {
        try {
            WebContextHolder.currentHttpServletRequest((HttpServletRequest) request);
            WebContextHolder.currentHttpServletResponse((HttpServletResponse) response);
            chain.doFilter(request, response);
        }
        finally {
            WebContextHolder.clear();
        }
    }
    
    public void destroy() {
        
    }
    
}
