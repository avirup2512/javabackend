package com.example.ecommerce;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.ecommerce.JWT.JWRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    JWRequestFilter jwRequestFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login").permitAll() 
                .requestMatchers("/createuser","/users/delete/**").hasRole("ADMIN")//Allow public access to specific endpoints
                .anyRequest().authenticated() // Require authentication for all other endpoints
            )
            .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Use stateless session (no sessions)
        http.addFilterBefore(jwRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
