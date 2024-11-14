package com.example.ecommerce.JWT;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.ecommerce.GlobalData.CurrentUser;
import com.example.ecommerce.Users.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JWRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtil jwtUtil;
    @Autowired
    UserRepository userRepository;

    CurrentUser

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) 
    throws ServletException, IOException
    {
        final String requestPath = req.getServletPath();
        // Skip JWT validation for public endpoints
        if (requestPath.equals("/login")) {
            chain.doFilter(req, res);
            return;
        }
        final String authorizationHeader = req.getHeader("Authorization");
        
        String email = null;
        String jwt = null;
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer "))
        {
            jwt = authorizationHeader.substring(7);
            email = jwtUtil.extractEmail(jwt);
        }
        if(email != null && SecurityContextHolder.getContext().getAuthentication() == null)
        {
            if(userRepository.findUserByEmail(email).size() > 0)
            {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    email,null,new ArrayList<>());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(req, res);
    }
}
