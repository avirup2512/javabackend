package com.example.ecommerce.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RestController
public class UserController {
    @Autowired
    UserRepository userRepository;

    // @PostMapping(path="/add")
    // public @ResponseBody String addNewUser(@RequestParam String userName, @RequestParam String userEmail, @RequestParam String password)
    // {
    //     UserModel u = new UserModel();
    //     u.setUserName(userName);
    //     u.setEmail(userEmail);
    //     u.setPassword(password);
    //     userRepository.save(u);
    //     return "Saved";
    // }
    @GetMapping("k")
    public String getMethodName() {
        return new String("HORI");
    }
    @PostMapping("/add")
    public String postMethodName(@RequestParam String userName, @RequestParam String userEmail, @RequestParam String password) {
        UserModel u = new UserModel();
        u.setUserName(userName);
        u.setEmail(userEmail);
        u.setPassword(password);
        userRepository.save(u);
        return "Saved";
    }
 
    
    
    
}
