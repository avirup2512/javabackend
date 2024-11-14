package com.example.ecommerce;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.example.ecommerce.Users.User;
import com.example.ecommerce.Users.UserRepository;
import com.example.ecommerce.Users.UserRole.Privilege.PrivilegeRepository;
import com.example.ecommerce.Users.UserRole.Privilege.UserPrivilege;
import com.example.ecommerce.Users.UserRole.Role.RoleRepository;
import com.example.ecommerce.Users.UserRole.Role.UserRole;

import jakarta.transaction.Transactional;

@Component
public class SetUpDataLoader implements  ApplicationListener <ContextRefreshedEvent> {

     private final String readPrivilege = "READ_PRIVILEGE";
     private final String writePrivilege = "WRITE_PRIVILEGE";
     private final String roleAdmin = "ROLE_ADMIN";
     private final String roleUser = "ROLE_USER";
    
   boolean alreadySetup = false;

   @Autowired 
   UserRepository userRepository;
   @Autowired
   PrivilegeRepository privilegeRepository;
   @Autowired
   RoleRepository roleRepository;

   private boolean checkAdmin()
   {
     UserPrivilege readPrivilege = privilegeRepository.findPrivilegeByName(this.readPrivilege);
     UserPrivilege writePrivilege = privilegeRepository.findPrivilegeByName(this.writePrivilege);
     if(readPrivilege == null || writePrivilege == null)
     {
          return false;
     }else{
          UserRole roleAdmin = roleRepository.findUserRoleByName(this.roleAdmin);
          if(roleAdmin == null)
          {
               return false;
          }else{
               Long roleId = roleAdmin.getId();
               System.out.println(roleAdmin.getName());
               System.out.println(roleId);
               if(userRepository.getUserByRole(roleId) != null)
               return true;
          }
     }
     return false;
   }

   @Override
   @Transactional
   public void onApplicationEvent(ContextRefreshedEvent event)
   {
        if(this.checkAdmin())
        return;
        UserPrivilege readPrivilege = createPrivillegeIfNotFound("READ_PRIVILEGE");
        UserPrivilege writPrivilege = createPrivillegeIfNotFound("WRITE_PRIVILEGE");
        
        List<UserPrivilege> adminPrivilege = Arrays.asList(readPrivilege,writPrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivilege);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        UserRole adminRole = roleRepository.findUserRoleByName("ROLE_ADMIN");
        User user = new User();
        user.setUserName("Babu Dada");
        user.setEmail("Babu@ubab.com");
        user.setPassword("123456");
        user.setRoles(Arrays.asList(adminRole));
        userRepository.save(user);
        alreadySetup = true;
   }

   @Transactional
   private UserPrivilege createPrivillegeIfNotFound(String name)
   {
        UserPrivilege privilege = privilegeRepository.findPrivilegeByName(name);
        if(privilege == null)
        {
            privilege = new UserPrivilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
   }
   @Transactional
   private UserRole createRoleIfNotFound(String name, List<UserPrivilege> privilegeList)
   {
        UserRole role = roleRepository.findUserRoleByName(name);
        if(role == null)
        {
            role = new UserRole(name);
            role.setPrivileges(privilegeList);
            roleRepository.save(role);
        }
        return role;
   }
}
