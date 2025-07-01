package com.gourav.YummiGoBackend.controller;

import com.gourav.YummiGoBackend.io.UserRequest;
import com.gourav.YummiGoBackend.io.UserResponse;
import com.gourav.YummiGoBackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody UserRequest request){
        return userService.registerUser(request);
    }
}
