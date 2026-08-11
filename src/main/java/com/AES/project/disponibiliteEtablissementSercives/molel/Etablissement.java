package com.AES.project.disponibiliteEtablissementSercives.molel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Table(name="Etablissement")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Etablissement {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id ;

    @NotBlank
    @Column(nullable=false)
    private String nomEtablissement;
    private String description ;

    @Builder.Default
    private Boolean actif=false;

    @Enumerated(EnumType.STRING)
    private Categorie categarie ;
//localisation******************
    @Column(nullable=false)
    private String adress;
    @NotBlank
    @Column(nullable=false)
    private String pays;
    @NotBlank
    @Column(nullable=false)
    private String ville;
    @Column(nullable=false)
    private String quartier;

//le gérant qui àa créé l'etablissement**************************
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_gerantPrincipal", unique = true)
    @JsonBackReference ("etablissementGerant")
    private User gerantPrincipal ;

    //La liste des gérant de cet etabissement
    @OneToMany(mappedBy="etablissementTravail", cascade=CascadeType.REMOVE,orphanRemoval=true)
    @JsonBackReference ("etablissementDuUser")
    private List<User> listeGerant ;

}
