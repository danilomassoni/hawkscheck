package com.hawkscheck.hawkscheck.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        System.out.println("Authorization Header: " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("Token não presente ou mal formado.");
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        System.out.println("JWT extraído: " + jwt);

        String email;
        try {
            email = jwtService.extractUsername(jwt);
            System.out.println("Email extraído do token: " + email);
        } catch (Exception e) {
            System.out.println("Erro ao extrair e-mail do token: " + e.getMessage());
            filterChain.doFilter(request, response);
            return;
        }

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            if (jwtService.isTokenValid(jwt, userDetails)) {
                String role = jwtService.extractClaim(jwt, claims -> claims.get("role", String.class));
                System.out.println("Role extraída do token: " + role);

                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, authorities);

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("Usuário autenticado com sucesso. Authorities: " + authorities);
            } else {
                System.out.println("Token inválido.");
            }
        }

        filterChain.doFilter(request, response);
    }
}
