package com.example.ecommerce.Users;

import java.util.List;

import com.example.ecommerce.Users.UserRole.Role.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity (name = "Users")
@Table(name = "Users")// this tells hibernate to make table out of this class
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    Long id;

    private String name;
    private String email;
    private String password;
    private Boolean active;

    public User()
    {
        
    }
    
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

    @ManyToMany
    @JoinTable
    (
        name="Users_Role_Sync",
        joinColumns = @JoinColumn(
            name="user_id"
        ),
        inverseJoinColumns = @JoinColumn(
            name="role_id"
        )
    )
    private List<UserRole> userRoles;
    public void setRoles(List<UserRole> roles)
    {
        this.userRoles = roles;
    }
}
