package com.hawkscheck.hawkscheck.security;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.hawkscheck.hawkscheck.model.User;
import com.hawkscheck.hawkscheck.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
            .withUsername(user.getEmail())
            .password(user.getPassword())
            .authorities("ROLE_" + user.getPaper().name()) // ou .roles(...) se preferir
            .build();
    }
}

