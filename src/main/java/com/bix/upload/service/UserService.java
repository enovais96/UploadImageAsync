package com.bix.upload.service;

import com.bix.upload.dto.AuthenticationDTO;
import com.bix.upload.dto.RegisterDTO;
import com.bix.upload.exception.RegisterException;
import com.bix.upload.exception.UnauthorizedException;
import com.bix.upload.model.User;
import com.bix.upload.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository repository;
    
    @Autowired
    EmailService emailService;
    
	@Autowired
    private AuthenticationManager authenticationManager;
    
    public UserDetails findByLogin(String login) {
    	return this.repository.findByLogin(login);
    }
    
    public User findUserWithLogin(AuthenticationDTO data) throws Exception {
		try {
			var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
	        var auth = this.authenticationManager.authenticate(usernamePassword);
	        
	        return (User) auth.getPrincipal();
		} catch(Exception e) {
			throw new UnauthorizedException(e.getMessage());
		}
	}
    
    public User createUser(RegisterDTO data) {
    	if(this.findByLogin(data.login()) != null) {
        	throw new RegisterException("Login already exists");
        }
    	
    	String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
    	
    	User newUser = new User(data.login(), encryptedPassword, data.role(), data.email());
    	
    	return this.saveUser(newUser);
    }
    
    private User saveUser(User user) throws UsernameNotFoundException {
        User userSaved = repository.save(user);
        
        if(userSaved != null) {
        	emailService.sendEmail(user.getEmail());
        }
        
        return userSaved;
    }
}