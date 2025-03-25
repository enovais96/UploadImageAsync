package com.bix.upload.controller;

import com.bix.upload.dto.AuthenticationDTO;
import com.bix.upload.dto.LoginResponseDTO;
import com.bix.upload.dto.RegisterDTO;
import com.bix.upload.model.User;
import com.bix.upload.service.TokenService;
import com.bix.upload.service.UserService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    
    @Autowired
    private UserService service;
    
    @Autowired
    private TokenService tokenService;   

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthenticationDTO data) throws Exception{
    	try {
    		User user = service.findUserWithLogin(data);
	        var token = tokenService.generateToken(user);
	
	        return ResponseEntity.ok(new LoginResponseDTO(token));
    	} catch(AuthenticationException e) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    	}
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterDTO data){
        try {
        	User user = this.service.createUser(data);

        	return ResponseEntity.ok(user);
        } catch (Exception e) {
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}