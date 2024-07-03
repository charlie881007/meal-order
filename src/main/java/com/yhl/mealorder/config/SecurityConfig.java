package com.yhl.mealorder.config;

import com.yhl.mealorder.entity.UserAccount;
import com.yhl.mealorder.exception.InvalidArgumentException;
import com.yhl.mealorder.filter.JwtRequestFilter;
import com.yhl.mealorder.repository.UserAccountRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
public class SecurityConfig {


    @Bean
    SecurityFilterChain configureSecurity(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                // h2 console 顯示問題
                .headers(httpSecurityHeadersConfigurer -> {
                    httpSecurityHeadersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable);
                })
                .authorizeHttpRequests(registry -> registry
                        .requestMatchers("/orders/*/status").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/orders").hasRole("USER")
                        .anyRequest().permitAll()
                )
                .httpBasic(Customizer.withDefaults());
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository) {
        return username -> {
            UserAccount userAccount = userAccountRepository.findByUsername(username);
            if (userAccount == null) {
                throw new InvalidArgumentException();
            }
            return userAccount.toUser();
        };
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }


    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
