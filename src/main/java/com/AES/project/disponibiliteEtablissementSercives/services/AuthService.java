package com.AES.project.disponibiliteEtablissementSercives.services;

import com.AES.project.disponibiliteEtablissementSercives.dto.LoginDtoRequest;
import com.AES.project.disponibiliteEtablissementSercives.dto.LoginDtoResponse;
import com.AES.project.disponibiliteEtablissementSercives.repository.UserRepository;
import com.AES.project.disponibiliteEtablissementSercives.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;





@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public LoginDtoResponse login(LoginDtoRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(userDetails);
        String role = userRepository.findByNomUtilisateur(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"))
                .getRole().name();
        LoginDtoResponse response = new LoginDtoResponse();
        response.setToken(token);
        response.setUsername(request.getUsername());
        response.setRole(role);
        return response;
    }
}