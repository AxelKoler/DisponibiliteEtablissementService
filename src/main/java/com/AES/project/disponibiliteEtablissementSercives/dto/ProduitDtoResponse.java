package com.AES.project.disponibiliteEtablissementSercives.dto;

import com.AES.project.disponibiliteEtablissementSercives.molel.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProduitDtoResponse {


    private Long id ;

    private String nomProduit;

    private String description ;
    private  int quantite ;

    private BigDecimal prix;

    private  Boolean disponible;

    //intervale de date pour lequel le produit n'est pas disponible
    private LocalDateTime debutDateIndisponibilite ;
    private  LocalDateTime finDateIndisponible ;

    //le gerant qui à créé ce produit*****************
    private User createur;
}
