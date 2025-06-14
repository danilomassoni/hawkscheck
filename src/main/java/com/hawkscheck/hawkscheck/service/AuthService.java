package com.hawkscheck.hawkscheck.service;

import com.hawkscheck.hawkscheck.dto.AuthRequestDTO;
import com.hawkscheck.hawkscheck.dto.AuthResponseDTO;
import com.hawkscheck.hawkscheck.model.User;
import com.hawkscheck.hawkscheck.repository.UserRepository;
import com.hawkscheck.hawkscheck.security.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired 
    private AuthenticationManager authenticationManager;
    
    @Autowired 
    private JwtService jwtService;

    @Autowired
    private UserRepository userRepository;

    //Para realizar a autenticação
    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        // Para buscar o usuário no banco de dados
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário not found"));

        // Gera o token JWT
        String jwtToken = jwtService.generateToken(user, user.getPaper().name());

        return new AuthResponseDTO(jwtToken, user.getName(), user.getPaper());
    }
}
