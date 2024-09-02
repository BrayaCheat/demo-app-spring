package com.example.bookStore.Models.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private long expiresIn;

    public String getToken(){
        return this.token;
    }

    public long getExpiresIn(){
        return this.expiresIn;
    }

    public LoginResponse setExpiresIn(long expiresIn){
        this.expiresIn = expiresIn;
        return this;
    }

    public LoginResponse setToken(String token){
        this.token = token;
        return this;
    }
}


