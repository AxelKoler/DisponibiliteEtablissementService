package com.AES.project.disponibiliteEtablissementSercives.controler;

import com.AES.project.disponibiliteEtablissementSercives.dto.EtablissementDtoRequest;
import com.AES.project.disponibiliteEtablissementSercives.dto.EtablisssementDtoResponse;
import com.AES.project.disponibiliteEtablissementSercives.molel.Etablissement;
import com.AES.project.disponibiliteEtablissementSercives.molel.User;
import com.AES.project.disponibiliteEtablissementSercives.repository.EtablissementRepository;
import com.AES.project.disponibiliteEtablissementSercives.repository.UserRepository;
import com.AES.project.disponibiliteEtablissementSercives.services.EtablissementService;
import com.AES.project.disponibiliteEtablissementSercives.services.ProduitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/etablissement")
@RequiredArgsConstructor
public class EtablissementController {

    private final EtablissementService etablissementService;
    private final EtablissementRepository etablissementRepository;
    private final UserRepository userRepository;
    private final ProduitService produitService;


    /// API de creation d 'établissement ************
    @PostMapping
    @PreAuthorize("hasRole('GERANT')")
    public ResponseEntity<EtablisssementDtoResponse> creerEtablissement(@RequestBody EtablissementDtoRequest etablissementRequest, Principal principal){
            User userConnected=userRepository.findByNomUtilisateur(principal.getName())
                    .orElseThrow(()->new RuntimeException("Utinisateur n'est pas connecté"));

            etablissementRequest.setGerantPrincipal(userConnected);

            return ResponseEntity.status(HttpStatus.CREATED).body(etablissementService.creerEtablissement(etablissementRequest));
    }


    ///Lister tous les etablissements(admin)***************************
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EtablisssementDtoResponse>>allProduit(){

        return ResponseEntity.ok(etablissementService.listTousEtablissement());
    }

    ///****Desactiver un etablissement( ADMIN , et GERANT CONNECTE)*********************



    ///***Supprimer etablissement**************************************
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('GERANT')")
    public ResponseEntity<Void> deleteEtablissement(@PathVariable Long id, Principal principal){
            User userConnected=userRepository.findByNomUtilisateur(principal.getName())
                    .orElseThrow(()->new RuntimeException("l'utilisateur n'est pas connecté"));


            ///verifier si l'utilisateur connecté == utilisateur gerant principal de l'etablissement
            Etablissement etablissement=etablissementRepository.findById(id)
                    .orElseThrow(()->new RuntimeException("Etablissement not found"));

            User gerantPrincipal= etablissement.getGerantPrincipal();
            String reponse="";

            //si utilisateur connected == gerant principal alors supprime
            if(userConnected.getMail().equals(gerantPrincipal.getMail())){
              reponse =etablissementService.supprimerEtablissement(id);
            }
            else{
                reponse="Vous n'etes pas le gérant";
            }

            System.out.println(reponse);
        return ResponseEntity.noContent().build();
    }
}
