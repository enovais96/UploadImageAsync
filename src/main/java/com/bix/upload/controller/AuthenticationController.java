package com.bix.upload.controller;

import com.bix.upload.dto.AuthenticationDTO;
import com.bix.upload.dto.LoginResponseDTO;
import com.bix.upload.dto.RegisterDTO;
import com.bix.upload.model.User;
import com.bix.upload.service.EmailService;
import com.bix.upload.service.TokenService;
import com.bix.upload.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
	
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService service;
    
    @Autowired
    private TokenService tokenService;   

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data){
    	
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data){
        if(this.service.findByLogin(data.login()) != null) {
        	return ResponseEntity.badRequest().build();
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = new User(data.login(), encryptedPassword, data.role(), data.email());

        this.service.saveUser(newUser);

        return ResponseEntity.ok().build();
    }
}