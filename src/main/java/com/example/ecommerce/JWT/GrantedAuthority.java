package com.example.ecommerce.JWT;

public class GrantedAuthority implements org.springframework.security.core.GrantedAuthority{
    
    String role;
    public GrantedAuthority(String role)
    {
        this.role = role;
    }
    @Override
    public String getAuthority() {
        // TODO Auto-generated method stub
        return this.role;
    }
}
