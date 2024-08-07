package com.yhl.mealorder.service;

import com.yhl.mealorder.DTO.UserRegistrationDTO;
import com.yhl.mealorder.entity.UserAccount;
import com.yhl.mealorder.exception.InvalidArgumentException;
import com.yhl.mealorder.repository.UserAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserAccountService {
    private PasswordEncoder passwordEncoder;
    private UserAccountRepository userAccountRepository;

    public UserAccountService(PasswordEncoder passwordEncoder, UserAccountRepository userAccountRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userAccountRepository = userAccountRepository;
    }

    public UserAccount findByUserName(String userName) {
        UserAccount userAccount = userAccountRepository.findByUsername(userName);

        if (userAccount == null) {
            throw new InvalidArgumentException();
        }

        return userAccount;
    }

    public void register(UserRegistrationDTO registrationDTO) {
        // 重複註冊檢查
        UserAccount userAccount = userAccountRepository.findByUsername(registrationDTO.getUsername());
        if (userAccount != null) {
            throw new InvalidArgumentException();
        }


        String encodedPassword = passwordEncoder.encode(registrationDTO.getPassword());

        userAccount = new UserAccount(registrationDTO.getUsername(), encodedPassword, "ROLE_USER");

        userAccountRepository.save(userAccount);
    }
}
