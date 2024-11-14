package com.example.ecommerce.Users.UserRole.Role;

import java.util.List;


import com.example.ecommerce.Users.User;
import com.example.ecommerce.Users.UserRole.Privilege.UserPrivilege;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="User_Role")
public class UserRole {
    public UserRole()
    {
        
    }
    public UserRole(String name)
    {
        this.name = name;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Long id;
    private String name;

    public Long getId()
    {
        return this.id;
    }

    @ManyToMany(mappedBy="userRoles")
    List<User> user;

    @ManyToMany
    @JoinTable(
        name="Role_Privilages_Sync",
        joinColumns = @JoinColumn(
            name="role_id"
        ),
        inverseJoinColumns = @JoinColumn
        (
            name="privilage_id"
        )
    )
    private List<UserPrivilege> privileges;

    public void setPrivileges(List<UserPrivilege> privilegeList) {
        this.privileges = privilegeList;
    }
    public List<UserPrivilege> getPrivilege()
    {
        return privileges;
    }
    public void setName(String name)
    {
        this.name = name;
    }
    public String getName()
    {
        return this.name;
    }
}
