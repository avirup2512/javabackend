package com.example.ecommerce.Audit.UserAudit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.ecommerce.Audit.Audit;
import com.example.ecommerce.GlobalVariable.AuditStatus;
import com.example.ecommerce.GlobalVariable.CRUDStatus;
import com.example.ecommerce.GlobalVariable.LoginEnum;
import com.example.ecommerce.Users.User;

public class UserAudit extends Audit{ 

    public Object principal;
    public User user;
    DateTimeFormatter time = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");

    public UserAudit(User user) 
    { 
        this.user = user;
    }
    public UserAudit(User user, Object principal) 
    { 
        this.user = user;
        this.principal = principal;
    }

    @Override
    public String createMessage(CRUDStatus s) {
        LocalDateTime now = LocalDateTime.now();
        switch (s) {
            case CREATE:
                this.message = principal+" created this user"+ user.getUserName()+" Time:"+ time.format(now);
                break;
            case DELETE:
                this.message = principal+" deleted this user"+ user.getUserName()+" Time:"+ time.format(now);
                break;
            case UPDATE:
                this.message = principal+" updated this user"+ user.getUserName()+" Time:"+ time.format(now);
                break;
            default:
                this.message = "";
                break;
        }
        return this.message;
    }
    public String createLoginMessage(LoginEnum status)
    {
        LocalDateTime now = LocalDateTime.now();
        switch (status) {
            case LOGINTRY:
                this.message = principal+"try to login."+ " Time:"+ time.format(now);
                break;
                case LOGGEDIN:
                this.message = user.getEmail()+" login successfully."+ " Time:"+ time.format(now);
                break;
                case LOGGEDOUT:
                this.message = principal+"logout successfully."+ " Time:"+ time.format(now);
                break;
                case FORGOTPASSWORD:
                this.message = principal+"access forgot password."+ " Time:"+ time.format(now);
                break;
            default:
                break;
            }
        return this.message;
    }
}
