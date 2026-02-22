package com.irestaurant.iPortalAPI.exception;


public class BaseException extends Exception {
    
    int code = 0;
    String message = "";
    
    public BaseException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    } 
    
    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
