package com.AES.project.disponibiliteEtablissementSercives.molel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="produit")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Produit {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id ;

    @NotBlank
    @Column(nullable=false)
    private String nomProduit;

    private String description ;
    private  int quantite ;
    private BigDecimal prix;

    //chemin de stockage de l'image
    private String image;


    @Builder.Default
    private  boolean disponible=false;

    //intervale de date pour lequel le produit n'est pas disponible
    private LocalDateTime debutDateIndisponibilite ;
    private  LocalDateTime finDateIndisponible ;

    //le gerant qui à créé ce produit*****************
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_createur")
    @JsonBackReference("produit-user")
    private User createur;


}
