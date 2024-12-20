package com.example.ecommerce.Users;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.Audit.AuditModel;
import com.example.ecommerce.Audit.AuditRepository;
import com.example.ecommerce.Audit.UserAudit.UserAudit;
import com.example.ecommerce.Email.EmailModel;
import com.example.ecommerce.GlobalVariable.AuditStatus;
import com.example.ecommerce.GlobalVariable.CRUDStatus;
import com.example.ecommerce.GlobalVariable.LoginEnum;
import com.example.ecommerce.Email.EmailService;
import com.example.ecommerce.JWT.JWTUtil;
import com.example.ecommerce.Response.Response;
import com.example.ecommerce.Users.UserRole.CustomUserQuery.UserAssignedRolesById;
import com.example.ecommerce.Users.UserRole.Privilege.PrivilegeRepository;
import com.example.ecommerce.Users.UserRole.Role.RoleRepository;
import com.example.ecommerce.Users.UserRole.Role.UserRole;

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

    @Autowired
    AuditRepository auditRepository;

    @GetMapping("users/all")
    private Iterable<User> getAllUser() {
        Iterable<User> users = userRepository.findAll();
        System.out.println("users");
        return users;
    }

    @GetMapping("user/{id}")
    public Response userDetails(@PathVariable Long id) {
        Response res = new Response();
        Optional<User> u = userRepository.findById(id);
        if (u.isPresent() == false) {
            res.message = "User is not exists";
            return res;
        } else {
            User user = u.get();
            res.data = user;
            res.message = "User is found successfully";
        }
        return res;
    }

    @DeleteMapping("users/delete/{email}")
    private Response putMethodName(@PathVariable String email) {
        Response res = new Response();
        ArrayList<User> users = userRepository.findUserByEmail(email);
        if (users.size() > 0) {
            userRepository.delete(users.get(0));
            res.message = "User has been deleted";
            this.setAudit(users.get(0), LoginEnum.NONE, AuditStatus.USER, CRUDStatus.DELETE);
        } else
            res.message = "User does not exists.";
        return res;
    }

    @PutMapping("users/edit/{id}")
    private Response editUser(@PathVariable Long id, @RequestBody User entity) {
        Response res = new Response();
        Optional<User> userEntity = userRepository.findById(id);
        User user = userEntity.get();
        System.out.println(entity);

        if (user != null
                && user.getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            if (entity.getEmail() != null)
                user.setEmail(entity.getEmail());
            if (entity.getUserName() != null)
                user.setUserName(entity.getUserName());

            userRepository.save(user);
            res.message = "User has been updated";
            this.setAudit(user, LoginEnum.NONE, AuditStatus.USER, CRUDStatus.UPDATE);
            return res;
        } else if (user == null) {
            res.message = "User not found";
        } else if (user != null
                && !user.getEmail().equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
            res.message = "User edit not permitted";
        }
        return res;
    }

    @PostMapping("/createuser")
    public Response CreateUser(@RequestParam String userName, @RequestParam String userEmail,
            @RequestParam String password,
            @RequestParam String roleName) {
        Response res = new Response();
        if (isExistsUser(userEmail)) {
            res.message = new String("User's email is already exists");
            return res;
        } else {
            UserRole role = roleRepository.findUserRoleByName(roleName);
            if (role == null) {
                res.message = new String("User's Role does not exists");
                return res;
            }
            User user = new User();
            user.setUserName(userName);
            user.setEmail(userEmail);
            user.setPassword(password);
            user.setRoles(Arrays.asList(role));
            userRepository.save(user);
            this.setAudit(user, LoginEnum.NONE, AuditStatus.USER, CRUDStatus.CREATE);
            res.message = new String("User has been created");
            res.data = user;
            return res;
        }
    }

    private ArrayList<User> findUsersByEmail(String email) {
        ArrayList<User> user = userRepository.findUserByEmail(email);
        System.out.println(user);
        return user;
    }

    private boolean isExistsUser(String email) {
        if (findUsersByEmail(email).size() == 0)
            return false;
        else
            return true;
    }

    @PostMapping("/login")
    public Response Login(@RequestParam String email, @RequestParam String password) {
        Response res = new Response();
        if (isExistsUser(email) == false) {
            res.message = new String("User does not exists");
        } else {
            User user = userRepository.authenticate(email, password);
            if (user == null) {
                res.message = new String("Credentials are not matched.");
            } else {
                UserAssignedRolesById users = userRepository.searchRoleByUserId(email);
                String token = jwtUtil.generateToken(email, users.getRoleName());
                res.token = token;
                res.message = new String("Success");
                this.setAudit(user, LoginEnum.LOGGEDIN, AuditStatus.USER, CRUDStatus.NONE);
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

    void setAudit(User user, LoginEnum loginEnum, AuditStatus auditStatus, CRUDStatus crudStatus) {
        Object email = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserAudit userAudit = new UserAudit(user, email);
        AuditModel audit = new AuditModel();
        if (loginEnum != LoginEnum.NONE) {
            audit.setMessage(userAudit.createLoginMessage(loginEnum));
        }
        if (crudStatus != CRUDStatus.NONE) {
            audit.setMessage(userAudit.createMessage(crudStatus));
        }
        audit.setStatus(auditStatus);
        auditRepository.save(audit);
    }
}
