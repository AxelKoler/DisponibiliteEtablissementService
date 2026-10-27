package com.AES.project.disponibiliteEtablissementSercives.controler;

import com.AES.project.disponibiliteEtablissementSercives.dto.LoginDtoRequest;
import com.AES.project.disponibiliteEtablissementSercives.dto.LoginDtoResponse;
import com.AES.project.disponibiliteEtablissementSercives.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginDtoResponse> login(@Valid @RequestBody LoginDtoRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}
