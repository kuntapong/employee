package com.kuntapong.employee.service.exception;

public class AuthServiceLoginFailException extends RuntimeException{

    public AuthServiceLoginFailException() {
        super("Login failed.");
    }
}
