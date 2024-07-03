package com.yhl.mealorder.filter;

import com.yhl.mealorder.entity.UserAccount;
import com.yhl.mealorder.exception.InvalidArgumentException;
import com.yhl.mealorder.repository.UserAccountRepository;
import com.yhl.mealorder.service.JwtService;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    UserAccountRepository userAccountRepository;

    public JwtRequestFilter() {
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String userId = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            try {
                userId = jwtService.extractUserId(jwt);
            } catch (MalformedJwtException e) {
                throw new InvalidArgumentException();
            }
        }

        if (userId != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            if (jwtService.validateToken(jwt)) {

                UserAccount userAccount = userAccountRepository.findById(Long.valueOf(userId)).orElseThrow(InvalidArgumentException::new);
                UserDetails userDetails = userDetailsService.loadUserByUsername(userAccount.getUsername());

                request.setAttribute("userAccount", userAccount);
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userAccount, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
