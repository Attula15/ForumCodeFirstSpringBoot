package com.bherincs.forumpractice.controllers;

import jakarta.annotation.security.RolesAllowed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(){
        return "Hello world";
    }

    @GetMapping("admin")
    public String admin(){
        return  "admin";
    }
}
