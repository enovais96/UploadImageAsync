package com.bix.upload.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bix.upload.exception.UnauthorizedException;
import com.bix.upload.repository.UserRepository;
import com.bix.upload.service.TokenService;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	
    @Autowired
    TokenService tokenService;
    
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    	try {
	        var token = this.recoverToken(request);
	        if(token != null){
	            var login = tokenService.validateToken(token);
	            UserDetails user = userRepository.findByLogin(login);
	
	            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	            SecurityContextHolder.getContext().setAuthentication(authentication);
	        }
	        filterChain.doFilter(request, response);
    	} catch (UnauthorizedException e) {
            handleUnauthorizedResponse(response, e.getMessage());
        } catch (Exception e) {
            handleUnauthorizedResponse(response, e.getMessage());
        }
    }
    
    private void handleUnauthorizedResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"" + message + "\"}");
        response.getWriter().flush();
    }

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}

