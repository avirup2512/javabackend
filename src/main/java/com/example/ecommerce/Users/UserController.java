package com.example.ecommerce.Users;

import java.net.http.HttpHeaders;
import java.security.Provider;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.Email.EmailModel;
import com.example.ecommerce.Email.EmailServiceInterface;
import com.example.ecommerce.Email.EmailService;
import com.example.ecommerce.JWT.JWTUtil;
import com.example.ecommerce.Response.Response;
import com.example.ecommerce.Users.UserRole.CustomUserQuery.UserAssignedRolesById;
import com.example.ecommerce.Users.UserRole.Privilege.PrivilegeRepository;
import com.example.ecommerce.Users.UserRole.Privilege.UserPrivilege;
import com.example.ecommerce.Users.UserRole.Role.RoleRepository;
import com.example.ecommerce.Users.UserRole.Role.UserRole;

import jakarta.persistence.Id;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;




@Controller
@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PrivilegeRepository privilegeRepository;

    @Autowired
    JWTUtil jwtUtil;
    
    @Autowired
    EmailService emailService;

    @GetMapping("users/all")
    private Iterable<User> getAllUser() {
        Iterable<User> users = userRepository.findAll();
        System.out.println("users");
        return users;
    }
    
    @DeleteMapping("users/delete/{email}")
    private Response putMethodName(@PathVariable String email) {
        Response res = new Response();
        ArrayList<User> users = userRepository.findUserByEmail(email);
        if(users.size() > 0)
        {
            userRepository.delete(users.get(0));
            res.message = "User has been deleted";
        }else
        res.message = "User does not exists.";
        return res;
    }

    @PutMapping("users/edit/{id}")
    private Response editUser(@PathVariable Long id, @RequestBody User entity) {
        Response res = new Response();
        Optional<User> userEntity = userRepository.findById(id);
        User user = userEntity.get();
        System.out.println(entity);
        
        if(user != null && user.getEmail() == SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        {
            if(entity.getEmail() != null)
            user.setEmail(entity.getEmail());
            if(entity.getUserName() != null)
            user.setUserName(entity.getUserName());

            userRepository.save(user);
            res.message = "User has been updated";
            return res;
        }else if (user == null)
        {
            res.message = "User not found";
        }else if(user != null && user.getEmail() != SecurityContextHolder.getContext().getAuthentication().getPrincipal())
        {
            res.message = "User edit not permitted";
        }
        return res;
    }

    @PostMapping("/createuser")
    public Response CreateUser(@RequestParam String userName, @RequestParam String userEmail, @RequestParam String password,
    @RequestParam String roleName, HttpServletRequest req) {
        Response res = new Response();
        if(isExistsUser(userEmail))
        {
            res.message = new String("User's email is already exists");
            return res;
        }else{
            UserRole role = roleRepository.findUserRoleByName(roleName);
            if(role == null)
            {
                res.message = new String("User's Role does not exists");
                return res;
            }
            User user = new User();
            user.setUserName(userName);
            user.setEmail(userEmail);
            user.setPassword(password);
            user.setRoles(Arrays.asList(role));
            userRepository.save(user);
            userRepository.save(user);
            res.message = new String("User has been created");
            res.data = user;
            return new Response();
        }
    }
    
    private ArrayList<User> findUsersByEmail(String email)
    {
        ArrayList<User> user = userRepository.findUserByEmail(email);
        System.out.println(user);
        return user;
    }
    private boolean isExistsUser(String email)
    {
        if(findUsersByEmail(email).size() == 0)
        return false;
        else
        return true;
    }
    @PostMapping("/login")
    public Response Login(@RequestParam String email, @RequestParam String password)
    {
        Response res = new Response(); 
        if(isExistsUser(email) == false)
        {
            res.message = new String("User does not exists");
        }else{
            User user = userRepository.authenticate(email, password);
            if(user == null)
            {
                res.message = new String("Credentials are not matched.");
            }else
            {
                UserAssignedRolesById users = userRepository.searchRoleByUserId(email);
                System.out.println(users.getRoleName());
                String token = jwtUtil.generateToken(email,users.getRoleName());
                res.token = token;
                res.message = new String("Success");
            }
        }
        return res;
    }
    @PostMapping("/forgottPassword")
    public String ForgotPassword() {
        EmailModel model = new EmailModel();
        model.recipient = "avirupc817@gmail.com";
        model.message = "bappa";
        model.subject = "EMNI";
        String res = emailService.sendEmail(model);
        return res;
    }
    
    
}
