package org.helpdesk.controller;

import org.helpdesk.entity.User;
import org.helpdesk.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService){
        this.authService = authService;
    }
    @PostMapping("/register")
    public User register(@RequestBody User user){
        return authService.registerUser(user.getName(), user.getEmail(), user.getPassword(), user.getRole());
    }
    @PostMapping("/login")
    public User login(@RequestBody User user){
        return authService.loginUser(user.getEmail(), user.getPassword());
    }
}
