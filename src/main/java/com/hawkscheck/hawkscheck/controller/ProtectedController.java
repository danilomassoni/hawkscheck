package com.hawkscheck.hawkscheck.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProtectedController {
    
    @GetMapping("/protected")
    public String protectedEndpoint() {
        return "You have sucessfully accessed a protected endpoint!";
    }
}
