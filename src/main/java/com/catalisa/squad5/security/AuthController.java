//package com.catalisa.squad5.security;
//
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//public class AuthController {
//
//    @PostMapping("/login")
//    public String login(@RequestBody LoginRequest loginRequest) {
//        // Authenticate the user and get the username
//        String username = authenticate(loginRequest.getUsername(), loginRequest.getPassword());
//
//        // Generate JWT token
//        String token = JwtUtil.generateToken(username);
//
//        return token;
//    }
//
//    // Authenticate the user (dummy implementation)
//    private String authenticate(String username, String password) {
//        // Perform authentication logic here
//        // Return the username if authentication is successful
//        return username;
//    }
//}