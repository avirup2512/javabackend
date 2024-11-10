package com.example.ecommerce.Users;

import java.net.http.HttpHeaders;
import java.security.SignatureException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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
    private String editUser(@PathVariable String id, @RequestBody String entity) {
        
        
        return entity;
    }

    @PostMapping("/createuser")
    public String CreateUser(@RequestParam String userName, @RequestParam String userEmail, @RequestParam String password) {
        if(isExistsUser(userEmail))
        {
            return new String("User's email is already exists");
        }else{
            User u = new User();
            u.setUserName(userName);
            u.setEmail(userEmail);
            u.setPassword(password);
            userRepository.save(u);
            return "Saved";
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
                String token = jwtUtil.generateToken(email);
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
