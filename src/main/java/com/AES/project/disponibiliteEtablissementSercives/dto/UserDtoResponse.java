package com.AES.project.disponibiliteEtablissementSercives.dto;

import com.AES.project.disponibiliteEtablissementSercives.molel.Etablissement;
import com.AES.project.disponibiliteEtablissementSercives.molel.Produit;
import com.AES.project.disponibiliteEtablissementSercives.molel.Role;
import com.AES.project.disponibiliteEtablissementSercives.molel.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class UserDtoResponse {


    private  Long id ;
    private String nomUtilisateur ;
    private Role role;
    private String mail;
    private String numero;

    private  boolean actif;

    //Localisation **************************************************
    private String ville;
    private String pays;
    private String adresse;

    //date de creation
    private LocalDateTime dateCreation;


    //Le gerant principal qui à créé ce gerant********************
    private User createur;

    //l'etablissement du gerant
    private Etablissement etablissementTravail;

}
