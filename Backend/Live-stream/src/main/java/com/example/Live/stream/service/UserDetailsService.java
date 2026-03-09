package com.example.Live.stream.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface UserDetailsService {


    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
    boolean existsByUsername(String username);
    boolean validateCredentials(String username, String password);
}