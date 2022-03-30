package com.project.jwtuserLogin.JWT;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter{

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer ")){
            UsernamePasswordAuthenticationToken jwt = getJWTfromHeader(header);
            SecurityContextHolder.getContext().setAuthentication(jwt);
        }
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getJWTfromHeader(String header) {
        String jwt = header.replace("Bearer " ,"");
        String user = JWT.require(Algorithm.HMAC256("hehe")).build().verify(jwt).getSubject();
        if(user != null)
            return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
        else{
            return null;
        }
    }

    
    
}
