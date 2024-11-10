package com.example.ecommerce.HTTPInterceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.example.ecommerce.JWT.JWTUtil;
@Component
public class HTTPinterceptor implements HandlerInterceptor {
    @Autowired
    JWTUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler)
    {
        // System.out.println(req.getRequestURL());
        // try {
        //     String token = jwtUtil.resolveToken(req);
        //     if(token != null)
        //     {
        //         if(jwtUtil.validateToken(token))
        //         return true;
        //     }
        // } catch (Exception e) {
        //     // TODO: handle exception
        // }
        
        return true;
    }
}
