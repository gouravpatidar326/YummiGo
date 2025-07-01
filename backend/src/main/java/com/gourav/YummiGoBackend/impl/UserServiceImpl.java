package com.gourav.YummiGoBackend.impl;

import com.gourav.YummiGoBackend.entity.UserEntity;
import com.gourav.YummiGoBackend.io.UserRequest;
import com.gourav.YummiGoBackend.io.UserResponse;
import com.gourav.YummiGoBackend.repo.UserRepo;
import com.gourav.YummiGoBackend.service.AuthenticationFacade;
import com.gourav.YummiGoBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationFacade authenticationFacade;

    @Override
    public UserResponse registerUser(UserRequest request) {
        UserEntity newUser = convertToEntity(request);
        newUser = userRepo.save(newUser);
        return convertToResponse(newUser);
    }

    @Override
    public String findByUserId() {
        String loggedInUserEmail = authenticationFacade.getAuthentication().getName();
        UserEntity loggedInUser = userRepo.findByEmail(loggedInUserEmail).orElseThrow(()-> new UsernameNotFoundException("User Not Found"));
        return loggedInUser.getId();
    }

    private UserEntity convertToEntity(UserRequest request){
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(request.getEmail());
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setName(request.getName());
        return userEntity;
    }

    private UserResponse convertToResponse(UserEntity registeredUser){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(registeredUser.getId());
        userResponse.setName(registeredUser.getName());
        userResponse.setEmail(registeredUser.getEmail());
        return  userResponse;
    }
}
