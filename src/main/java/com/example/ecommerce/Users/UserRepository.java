package com.example.ecommerce.Users;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long>{
    
    @Query(value = "SELECT * FROM user WHERE email=:email", nativeQuery = true)
    ArrayList<User> findUserByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM user WHERE email=:email AND password=:password", nativeQuery = true)
    User authenticate(@Param("email") String email, @Param("password") String password);
    
}
