package com.AES.project.disponibiliteEtablissementSercives.molel;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="utilisateur")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private  Long id ;

    @NotBlank
    @Column(nullable=false)
    private String nomUtilisateur ;


    @NotBlank
    @Column(nullable=false)
    private String password ;

    @NotBlank
    @Column(nullable=false)
    private String mail ;


    private String telephone ;

    //Le role de l'utilisateur(ADMIN, gerant, client)
    @Enumerated(EnumType.STRING)
    private  Role role;

    //rendre le compte actif et desactif au lieu de le supprimer
    @Column(nullable=false)
    @Builder.Default
    private  Boolean actif=false;

    //Localisation **************************************************
    private String ville;
    private String pays;
    private String adresse;

    //date de creation
    @Column(nullable=false)
    @Builder.Default
    private LocalDateTime dateCreation= LocalDateTime.now();

    //**Liste les gérants crées par ce gerant principal***************
    @OneToMany(mappedBy="createur")
    private List<User> listeGerantCree;

    //Le gerant principal qui à créé ce gerant********************
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_creator")
    private User createur;

    //l'etablissement créés par ce gerant********************
    @OneToOne(mappedBy="gerantPrincipal", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonManagedReference("etablissementGerant")
    private Etablissement etablissementCree;

    //La liste des produits crées par ce gerant**************
    @OneToMany(mappedBy="createur",fetch = FetchType.LAZY)
    @JsonBackReference("produit-user")
    private List<Produit>listProduits;


    //l'etablissement du gerant
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_etablissement")
    @JsonManagedReference("etablissementDuUser")
    private Etablissement etablissementTravail;
}
