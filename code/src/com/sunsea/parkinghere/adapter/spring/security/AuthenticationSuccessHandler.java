package com.sunsea.parkinghere.adapter.spring.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.sunsea.parkinghere.biz.helper.AuthenticationHelper;
import com.sunsea.parkinghere.biz.model.AuthenticationHistory;
import com.sunsea.parkinghere.biz.service.Adsfddsfdsf;

public class AuthenticationSuccessHandler extends
                                         SimpleUrlAuthenticationSuccessHandler {
    
    @Autowired
    private Adsfddsfdsf authenticationService;
    
    public AuthenticationSuccessHandler() {
        super();
    }
    
    public AuthenticationSuccessHandler(String defaultTargetUrl) {
        super(defaultTargetUrl);
    }
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException,
                                                                      ServletException {
        AuthenticationHistory authenticationHistory = AuthenticationHelper.authenticationHistoryFromHttpRequest(request);
        authenticationHistory.setAuthType("form");
        authenticationService.a5(authenticationHistory);
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
