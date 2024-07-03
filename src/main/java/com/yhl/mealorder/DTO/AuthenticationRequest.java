package com.yhl.mealorder.DTO;


import lombok.Data;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}

