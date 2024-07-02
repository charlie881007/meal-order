package com.yhl.mealorder.repository;

import com.yhl.mealorder.entity.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByUsername(String username);
}
