package com.AES.project.disponibiliteEtablissementSercives.dto;

import com.AES.project.disponibiliteEtablissementSercives.molel.Etablissement;
import com.AES.project.disponibiliteEtablissementSercives.molel.Produit;
import com.AES.project.disponibiliteEtablissementSercives.molel.Role;
import com.AES.project.disponibiliteEtablissementSercives.molel.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDtoRequest {

    @NotBlank(message = "nom obligatoire")
    private String nom;

    @NotBlank(message = "mot de passe obligatoire")
    @Size(min = 6)
    private String password;

    @NotBlank(message = "email  obligatoire")
    @Email
    private String mail ;


    private String telephone ;

    @NotNull
    private Role role;

    //Localisation **************************************************
    private String ville;
    private String pays;
    private String adresse;

    //Le gerant principal qui à créé ce gerant********************
    private User createur;

    //l'etablissement du gerant
    private Etablissement etablissementTravail;
}



