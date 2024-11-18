package com.example.ecommerce.Audit;

import com.example.ecommerce.GlobalVariable.CRUDStatus;

public abstract class Audit {
    
    public String message;

    public abstract String createMessage(CRUDStatus params);
}
