package com.AES.project.disponibiliteEtablissementSercives.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDtoResponse {

    private Long id;

    private String username;

    private String role;

    private String token;


}