package com.AES.project.disponibiliteEtablissementSercives.services;

import com.AES.project.disponibiliteEtablissementSercives.dto.EtablissementDtoRequest;
import com.AES.project.disponibiliteEtablissementSercives.dto.EtablisssementDtoResponse;
import com.AES.project.disponibiliteEtablissementSercives.molel.Etablissement;
import com.AES.project.disponibiliteEtablissementSercives.repository.EtablissementRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EtablissementService {
    private final EtablissementRepository etablissementRepository;

    ///methode qui transforme un etablissement en etablissement response

    private EtablisssementDtoResponse transformDto(Etablissement etablissement){

        EtablisssementDtoResponse etablissementResponse=new EtablisssementDtoResponse();

        etablissementResponse.setId(etablissement.getId());
        etablissementResponse.setNomEtablissement(etablissement.getNomEtablissement());
        etablissementResponse.setDescription(etablissement.getDescription());
        etablissementResponse.setCategorie(etablissement.getCategarie());
        etablissementResponse.setActif(etablissement.getActif());
        etablissementResponse.setAdress(etablissement.getAdress());
        etablissementResponse.setPays(etablissement.getPays());
        etablissementResponse.setVille(etablissement.getVille());
        etablissementResponse.setQuartier(etablissement.getQuartier());
        etablissementResponse.setGerantPrincipal(etablissement.getGerantPrincipal());



        return etablissementResponse;

    }


    ///***** Creer un etablissement avec un compte gerant ************************************

    public EtablisssementDtoResponse creerEtablissement(EtablissementDtoRequest etablissementRequest){

        Etablissement etablissement=new Etablissement();

        etablissement.setNomEtablissement(etablissementRequest.getNomEtablissement());
        etablissement.setDescription(etablissementRequest.getDescription());
        etablissement.setCategarie(etablissementRequest.getCategorie());
        etablissement.setAdress(etablissementRequest.getAdress());
        etablissement.setPays(etablissementRequest.getPays());
        etablissement.setVille(etablissementRequest.getVille());
        etablissement.setQuartier(etablissementRequest.getQuartier());
        etablissement.setGerantPrincipal(etablissementRequest.getGerantPrincipal());

        ///sauvegarder dans la bd
        etablissementRepository.save(etablissement);
        return transformDto(etablissement);
    }



    ///******   Liste tous les etablissements**********************************************
    public List<EtablisssementDtoResponse> listTousEtablissement(){

        List<Etablissement>listEtablissement=etablissementRepository.findAll();

        //transformer tous les etablissement en etablissementdtoResponse

        List<EtablisssementDtoResponse>etablisssementDtoResponses=listEtablissement.stream()
                .map(this::transformDto)
                .toList();

        return etablisssementDtoResponses;
    }


    ///*****supprimer un etablissement********************************

    public String supprimerEtablissement(Long id){

        Etablissement etablissement=etablissementRepository.findById(id)
                            .orElseThrow(()->new RuntimeException("Etablissement introuvable: "+id));
        etablissementRepository.delete(etablissement);

        return "l'etablissement "+etablissement.getNomEtablissement()+"à été supprimé avec succes" ;
    }


    ///**Valider un etablissement (seul l'admin)****************************************
    @Transactional
    public EtablisssementDtoResponse validerEtablissement(Long id){

        //verifier si l'etablissement existe
        Etablissement etablissement = etablissementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Établissement introuvable avec l'id : " + id));

        //Activer si false et desactiver si true
        if(etablissement.getActif()==false){
            etablissement.setActif(true);
        }
        else{
            etablissement.setActif(false);
        }

        //sauvegarder l'etablissement
        etablissementRepository.save(etablissement);

        //transformer l' etablissement en etablissement response
        EtablisssementDtoResponse etablissementDtoResponse=transformDto(etablissement);

        return etablissementDtoResponse;
    }

}
