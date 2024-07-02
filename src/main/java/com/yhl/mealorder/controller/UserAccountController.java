package com.yhl.mealorder.controller;


import com.yhl.mealorder.DTO.UserRegistrationDTO;
import com.yhl.mealorder.service.UserAccountService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/userAccount")
public class UserAccountController {
    UserAccountService userAccountService;

    public UserAccountController(UserAccountService userAccountService){
        this.userAccountService = userAccountService;
    }

    @PostMapping
    public void register (@RequestBody UserRegistrationDTO registrationDTO){
        userAccountService.register(registrationDTO);
    }
}
