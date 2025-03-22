package com.bix.upload.service;

import com.bix.upload.model.User;
import com.bix.upload.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository repository;
    
    @Autowired
    EmailService emailService;
    
    public UserDetails findByLogin(String login) {
    	return this.repository.findByLogin(login);
    }
    
    public User saveUser(User user) throws UsernameNotFoundException {
        User userSaved = repository.save(user);
        
        if(userSaved != null) {
        	emailService.sendEmail(user.getEmail());
        }
        
        return userSaved;
    }
}