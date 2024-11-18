package com.example.ecommerce.Audit;

import com.example.ecommerce.GlobalVariable.AuditStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity(name="audit")
@Table(name="audit")
public class AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    Long id;

    @Column(name="message")
    String message;
    @Column(name="time")
    String time;
    @Column(name="status")
    String status;

    public void setAuditId(Long id)
    {
        this.id = id;
    }
    public Long getAuditId()
    {
        return this.id;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
    public String getMessage()
    {
        return this.message;
    }
    public void setTime(String time)
    {
        this.time = time;
    }
    public String getTime()
    {
        return this.time;
    }
    public void setStatus(AuditStatus s)
    {
        switch (s) {
            case USER:
                this.status = "user";
                break;
        
            default:
                break;
        }
    }
    public String getStatus()
    {
        return this.status;
    }
}
