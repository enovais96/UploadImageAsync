package com.bix.upload.service;

import com.bix.upload.model.User;
import com.bix.upload.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UserRepository repository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByLogin(username);
    }
    
    public User getUserLogged() {
    	var authentication = SecurityContextHolder.getContext().getAuthentication();
		
		User user = (User) authentication.getPrincipal();
		
		return user;
    }
}