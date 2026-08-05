package com.AES.project.disponibiliteEtablissementSercives.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDtoRequest {

    @NotBlank(message="username obligatoire")
    private String username;

    @NotBlank(message="password obligatoire")
    private String password;

}
