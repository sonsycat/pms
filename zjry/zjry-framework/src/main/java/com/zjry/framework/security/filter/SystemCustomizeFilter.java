package com.zjry.framework.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.zjry.framework.web.service.IVerificationService;

@Component
public class SystemCustomizeFilter extends OncePerRequestFilter {
//    @Autowired
//    private IVerificationService customizeService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException
    {
    	
//        customizeService.verify();
        chain.doFilter(request, response);
    }
}
