package com.AES.project.disponibiliteEtablissementSercives.controler;

import com.AES.project.disponibiliteEtablissementSercives.dto.ProduitDtoRequest;
import com.AES.project.disponibiliteEtablissementSercives.dto.ProduitDtoResponse;
import com.AES.project.disponibiliteEtablissementSercives.molel.Etablissement;
import com.AES.project.disponibiliteEtablissementSercives.molel.Produit;
import com.AES.project.disponibiliteEtablissementSercives.molel.User;
import com.AES.project.disponibiliteEtablissementSercives.repository.EtablissementRepository;
import com.AES.project.disponibiliteEtablissementSercives.repository.ProduitRepository;
import com.AES.project.disponibiliteEtablissementSercives.repository.UserRepository;
import com.AES.project.disponibiliteEtablissementSercives.services.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/produit")
@RequiredArgsConstructor
public class ProduitController {

    private final ProduitService produitService;
    private final ProduitRepository produitRepository;
    private final UserRepository userRepository;
    private final EtablissementRepository etablissementRepository;


    ///**api pour creer produit
    @PostMapping
    @PreAuthorize("hasRole('GERANT')")
    public ResponseEntity<ProduitDtoResponse> creerProduit(@RequestBody ProduitDtoRequest produitRequest, Principal principal){

        User userConnected=userRepository.findByNomUtilisateur(principal.getName())
                .orElseThrow(()->new RuntimeException("utlisateur non connecté"));

        produitRequest.setCreateur(userConnected);

        return ResponseEntity.status(HttpStatus.CREATED).body(produitService.creerProduit(produitRequest));
    }


    ///Lister tous les produits(admin)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ProduitDtoResponse>>allProduits(){

        return ResponseEntity.ok(produitService.listTousProduit());
    }



    ///lister que les de l'etablissement (GERANT)




    ///****api pour supprimer un produit*******************************

    public ResponseEntity<Void> deleteProduit(@PathVariable Long id, Principal principal){

        User userConnected= userRepository.findByNomUtilisateur(principal.getName())
                .orElseThrow(()->new RuntimeException("Utilisateur n'est pas connecté"));



        ///etablissement du user connected
        String etablissementUserConnected= userConnected.getEtablissementTravail().getNomEtablissement();

        ///etablissement du produit
        Produit produit= produitRepository.findById(id)
                .orElseThrow(()->new RuntimeException("produit not found"));
        String etablissementDuProduit=produit.getCreateur().getEtablissementTravail().getNomEtablissement();

        ///Verifier si le user connecté est un gerant du mem etablissement, si oui supprimer
        String reponse="";
        if(etablissementUserConnected.equals(etablissementDuProduit)){
           reponse= produitService.supprimerProduit(id);
        }else{
            reponse="vous n'etes pas de cet etablissement";
        }
        System.out.println(reponse);

        return ResponseEntity.noContent().build();
    }

}
