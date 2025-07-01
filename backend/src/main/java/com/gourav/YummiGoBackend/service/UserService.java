package com.gourav.YummiGoBackend.service;

import com.gourav.YummiGoBackend.io.UserRequest;
import com.gourav.YummiGoBackend.io.UserResponse;

public interface UserService {

    UserResponse registerUser(UserRequest request);

    String findByUserId();
}
