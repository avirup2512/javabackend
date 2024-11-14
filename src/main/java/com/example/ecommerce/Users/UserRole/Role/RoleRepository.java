package com.example.ecommerce.Users.UserRole.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends CrudRepository<UserRole, Long> {

    @Query(value="SELECT * FROM User_Role WHERE name=:name", nativeQuery = true)
    UserRole findUserRoleByName(@Param("name") String name);

    @Query(value="SELECT id FROM User_Role WHERE name=:name", nativeQuery = true)
    UserRole findUserRoleIdByName(@Param("name") String name);
} 