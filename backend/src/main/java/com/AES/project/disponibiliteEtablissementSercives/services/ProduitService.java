package com.AES.project.disponibiliteEtablissementSercives.services;

import com.AES.project.disponibiliteEtablissementSercives.dto.ProduitDtoRequest;
import com.AES.project.disponibiliteEtablissementSercives.dto.ProduitDtoResponse;
import com.AES.project.disponibiliteEtablissementSercives.molel.Produit;
import com.AES.project.disponibiliteEtablissementSercives.repository.ProduitRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.constraintvalidators.bv.number.sign.PositiveValidatorForDouble;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProduitService {

    private final ProduitRepository produitRepository;


    //Transfomer le produit en produit response

    private ProduitDtoResponse transformDto(Produit produit){

        ProduitDtoResponse produitResponse = new ProduitDtoResponse();

        produitResponse.setId(produit.getId());
        produitResponse.setNomProduit(produit.getNomProduit());
        produitResponse.setDescription(produit.getDescription());
        produitResponse.setDisponible(produit.isDisponible());
        produitResponse.setPrix(produit.getPrix());
        produitResponse.setQuantite(produit.getQuantite());
        produitResponse.setDebutDateIndisponibilite(produit.getDebutDateIndisponibilite());
        produitResponse.setFinDateIndisponible(produit.getFinDateIndisponible());
        produitResponse.setCreateur(produit.getCreateur());


        return produitResponse;
    }


    ///**** creer un produit **************************************************************

    public ProduitDtoResponse creerProduit(ProduitDtoRequest produitRequest){

        Produit produit=new Produit();

        produit.setNomProduit(produitRequest.getNomProduit());
        produit.setDescription(produitRequest.getDescription());
        produit.setQuantite(produitRequest.getQuantite());
        produit.setPrix(produitRequest.getPrix());
        produit.setDebutDateIndisponibilite(produitRequest.getDebutDateIndisponibilite());
        produit.setFinDateIndisponible(produitRequest.getFinDateIndisponible());
        produit.setCreateur(produitRequest.getCreateur());

        if(produit.getCreateur().getEtablissementCree().getActif()==false){
            throw new RuntimeException("Votre etablissement est en attente d'activation ");
        }
        else if(produitRepository.existsByNomProduit(produit.getNomProduit())){
            throw new RuntimeException("produit existe deja");
        }
        else{
            produitRepository.save(produit);
        }

        return transformDto(produit);
    }



    ///*****Liste de tous les produits***************************************************
    public List<ProduitDtoResponse>listTousProduit(){

        List<Produit>listProduit=produitRepository.findAll();


        //transformer en produitdtoresponse
        List<ProduitDtoResponse>listProduiResponse=listProduit.stream()
                .map(this::transformDto)
                .toList();

        return listProduiResponse;
    }




    ///****** supprimer un produit ***********************************************************
    public String supprimerProduit(Long id){

        Produit produit=produitRepository.findById(id)
                .orElseThrow(()->new RuntimeException("produit non trouvé"));

        if(produit.getCreateur().getEtablissementCree().getActif()==false){
            throw new RuntimeException("Votre etablissement est en attente d'activation ");
        }
        else produitRepository.delete(produit);

        return "Le produit "+produit.getNomProduit()+" à été supprimé avec succes";
    }
}
