package com.example.ecommerce.Users.UserRole.CustomUserQuery;

import java.util.List;

import com.example.ecommerce.Users.UserRole.Role.UserRole;

public class UserAssignedRolesById {
    
    private Long user_id;          // Maps to AS user_id
    private String user_name;      // Maps to AS user_name
    private String user_email;     // Maps to AS user_email
    private Long sync_role_id;      // Maps to AS sync_role_id
    private Long role_id;          // Maps to AS role_id
    private String role_name;  

    public String getRoleName()
    {
        return role_name;
    }
}
