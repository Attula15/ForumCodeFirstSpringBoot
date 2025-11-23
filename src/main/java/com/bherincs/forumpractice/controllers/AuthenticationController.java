package com.bherincs.forumpractice.controllers;

import com.bherincs.forumpractice.controllers.dto.UserLoginDTO;
import com.bherincs.forumpractice.service.JwtService;
import com.bherincs.forumpractice.service.UserInfoService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("/auth")
public class AuthenticationController {

    private UserInfoService service;
    private JwtService jwtService;
    private AuthenticationManager authManager;

    public AuthenticationController(UserInfoService service, JwtService jwtService, AuthenticationManager authManager) {
        this.service = service;
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    @PostMapping("/login")
    public String Login(@RequestBody UserLoginDTO loginData){
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginData.getUsername(), loginData.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(loginData.getUsername());
        }
        else{
            throw new UsernameNotFoundException("Invalid username or password!");
        }
    }

    @PostMapping("/register")
    public String Register(@RequestBody UserLoginDTO registerData){
        return service.addUser(registerData);
    }
}
