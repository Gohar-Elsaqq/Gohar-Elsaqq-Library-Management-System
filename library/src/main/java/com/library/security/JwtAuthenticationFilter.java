package com.library.security;

import com.library.service.AppUserServices;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Log4j2
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private AppUserServices appUserServices;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        SecurityContext securityContext = SecurityContextHolder.getContext();
        if (authorizationHeader != null && securityContext.getAuthentication()==null){
            String jwtToken = authorizationHeader.substring("Bearer ".length());
            if(jwtTokenUtils.validateToken(jwtToken,request)){
              String username = jwtTokenUtils.getUsernameFromToken(jwtToken);
              if(username!=null){
                  AppUserDatiles appUserDatiles= (AppUserDatiles) appUserServices.loadUserByUsername(username);
                  if(jwtTokenUtils.isTokenValid(jwtToken,appUserDatiles)){
                      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(appUserDatiles, null, appUserDatiles.getAuthorities());
                      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                      securityContext.setAuthentication(authenticationToken);
                      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                  }
                  }
              }
            }
        filterChain.doFilter(request, response);
        }

    }
