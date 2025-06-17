package com.hawkscheck.hawkscheck.controller;

import com.hawkscheck.hawkscheck.dto.AuthRequestDTO;
import com.hawkscheck.hawkscheck.dto.AuthResponseDTO;
import com.hawkscheck.hawkscheck.dto.JwtAuthenticationResponse;
import com.hawkscheck.hawkscheck.dto.UserDto;
import com.hawkscheck.hawkscheck.dto.UserRequestDTO;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String email = userDetails.getUsername();
            
            User user = userRepository.findByEmail(email)
              .orElseThrow(() -> new UsernameNotFoundException("User not found"));

            String token = jwtService.generateToken(user, user.getPaper().name());

            return ResponseEntity.ok( 
                new AuthResponseDTO(token, user.getName(), user.getPaper())
            );


        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body("Invalids credentials, please try again.");
        } catch (Exception e) {
            e.printStackTrace(); // ou use um logger
            return ResponseEntity.status(500).body("Error during authentication, please try again later.");
        }
    }

//Registration endpoint
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email already in use");
        }

        User user = new User();
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setPaper(userRequestDTO.getPaper());

        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }
}
