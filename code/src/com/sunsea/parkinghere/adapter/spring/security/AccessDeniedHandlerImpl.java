package com.sunsea.parkinghere.adapter.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.WebAttributes;

import com.sunsea.parkinghere.framework.utils.JsonUtils;
import com.sunsea.parkinghere.framework.web.WebUtils;

public class AccessDeniedHandlerImpl extends
                                    org.springframework.security.web.access.AccessDeniedHandlerImpl {
    
    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException,
                                                                   ServletException {
        if (!response.isCommitted()) {
            if ("application/json".equalsIgnoreCase(request.getHeader("Content-Type"))) {
                // Put exception into request scope (perhaps of use to a view)
                request.setAttribute(WebAttributes.ACCESS_DENIED_403,
                                     accessDeniedException);
                
                // Set the 403 status code.
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                String jsonString = JsonUtils.toJson(WebUtils.failedMap("拒绝访问！"));
                response.getWriter().write(jsonString);
                response.flushBuffer();
            }
            else {
                super.handle(request, response, accessDeniedException);
            }
        }
    }
}
