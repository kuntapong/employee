package com.kuntapong.employee.controller;

import com.kuntapong.employee.controller.request.LoginRequest;
import com.kuntapong.employee.controller.response.AuthResponse;
import com.kuntapong.employee.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value = "api/v1/auth")
public class AuthController {

    protected static final Logger log = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        log.info("Login username({}).", req.getUsername());

        String token = authService.login(req.getUsername(), req.getPassword());
        return new AuthResponse(token);
    }

}
