package com.example.ecommerce.Users.UserRole.Privilege;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface PrivilegeRepository extends CrudRepository<UserPrivilege,Long> {
    
    @Query(value="SELECT * FROM User_Privilege WHERE name=:name", nativeQuery = true)
    UserPrivilege findPrivilegeByName(@Param("name") String name);
}
