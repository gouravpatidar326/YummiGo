package com.gourav.YummiGoBackend.io;

import lombok.AllArgsConstructor;
import lombok.Getter;


public class AuthenticationResponse {
    private String email;
    private String token;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
