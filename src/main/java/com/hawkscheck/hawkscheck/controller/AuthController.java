package com.hawkscheck.hawkscheck.controller;

import com.hawkscheck.hawkscheck.dto.AuthenticationRequest;
import com.hawkscheck.hawkscheck.dto.JwtAuthenticationResponse;
import com.hawkscheck.hawkscheck.dto.UserDto;
import com.hawkscheck.hawkscheck.model.User;
import com.hawkscheck.hawkscheck.repository.UserRepository;
import com.hawkscheck.hawkscheck.security.JwtService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            String role = userDetails.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .orElse("ROLE_USER");

            String token = jwtService.generateToken(userDetails, role);
            return ResponseEntity.ok(new JwtAuthenticationResponse(token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Credenciais inválidas.");
        } catch (Exception e) {
            e.printStackTrace(); // ou use um logger
            return ResponseEntity.status(500).body("Erro interno durante autenticação.");
        }
    }


    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserDto userDto) {
        if (userRepository.existsByEmail(userDto.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setPaper(userDto.getPaperEnum());

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
