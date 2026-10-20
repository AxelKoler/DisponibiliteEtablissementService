package com.AES.project.disponibiliteEtablissementSercives.dto;

import com.AES.project.disponibiliteEtablissementSercives.molel.Categorie;
import com.AES.project.disponibiliteEtablissementSercives.molel.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;
@Data
public class EtablisssementDtoResponse {


    private Long id ;
    private String nomEtablissement;
    private String description ;
    private Categorie categorie;
    private Boolean actif;
    //localisation******************

    private String adress;
    private String pays;
    private String ville;
    private String quartier;

    //le gérant qui àa créé l'etablissement**************************
    private User gerantPrincipal ;

}
