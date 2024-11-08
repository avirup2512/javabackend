package com.example.ecommerce.Users;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // this tells hibernate to make table out of this class
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;
    String email;
    String password;
    
    public void setUserId(long Id)
    {
        this.id = Id;
    }
    public long getUserId()
    {
        return this.id;
    }
    public void setUserName(String name)
    {
        this.name = name;
    }
    public String getUserName()
    {
        return this.name;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }
    public String getEmail()
    {
        return this.email;
    }
    public void setPassword(String password)
    {
        this.password = password;
    }
    public String getPassword()
    {
        return this.password;
    }
}
