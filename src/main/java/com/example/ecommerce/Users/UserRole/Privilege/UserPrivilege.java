package com.example.ecommerce.Users.UserRole.Privilege;

import java.util.List;

import com.example.ecommerce.Users.UserRole.Role.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="User_Privilege")
public class UserPrivilege {
    public UserPrivilege()
    {

    }
    public UserPrivilege(String name)
    {
        this.name = name;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    @ManyToMany(mappedBy = "privileges")
    private List<UserRole> roles;

    public void setName(String name)
    {
        this.name = name;
    }
    public void setRoles(List<UserRole> roles)
    {
        this.roles = roles;
    }
}
