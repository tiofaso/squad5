package com.catalisa.squad5.controller;

import com.catalisa.squad5.dtos.AuthenticationDTO;
import com.catalisa.squad5.dtos.LoginTokenDTO;
import com.catalisa.squad5.dtos.RegisterDTO;
import com.catalisa.squad5.model.Users;
import com.catalisa.squad5.repository.UsersRepository;
import com.catalisa.squad5.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/issues/auth")
public class AuthenticationController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO authenticationDTO) {

        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.getUsernameDto(),
                                                                    authenticationDTO.getPasswordDto());

        var authentication = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.createToken((Users) authentication.getPrincipal());

        return ResponseEntity.ok(new LoginTokenDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterDTO registerDTO) {
        if(this.usersRepository.findByUsername(registerDTO.getUsernameDTO()) != null) return ResponseEntity.badRequest().build();

        String passwordHash = new BCryptPasswordEncoder().encode(registerDTO.getPasswordDTO());

        Users newUser = new Users(registerDTO.getUsernameDTO(), passwordHash, registerDTO.getRoles());

        usersRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
