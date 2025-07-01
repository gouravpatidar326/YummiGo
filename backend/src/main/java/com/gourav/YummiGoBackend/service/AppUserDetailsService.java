package com.gourav.YummiGoBackend.service;

import com.gourav.YummiGoBackend.entity.UserEntity;
import com.gourav.YummiGoBackend.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepo.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        return new User(user.getEmail(), user.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
    }
}

