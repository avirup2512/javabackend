package com.example.ecommerce.Users;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.ecommerce.Users.UserRole.CustomUserQuery.UserAssignedRolesById;


public interface UserRepository extends CrudRepository<User, Long>{
    
    @Query(value = "SELECT * FROM Users WHERE email=:email", nativeQuery = true)
    ArrayList<User> findUserByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM Users WHERE email=:email AND password=:password", nativeQuery = true)
    User authenticate(@Param("email") String email, @Param("password") String password);

    
    @Query(value="SELECT * FROM Users_Role_Sync INNER JOIN Users ON (Users.id = Users_Role_Sync.user_id) WHERE Users_Role_Sync.role_id=:roleId",nativeQuery = true)
    User getUserByRole(@Param("roleId") Long id);

    @Query(name = "User.findUserRolesByEmail", nativeQuery = true)
    UserAssignedRolesById searchRoleByUserId(@Param("email") String email);
    
}
