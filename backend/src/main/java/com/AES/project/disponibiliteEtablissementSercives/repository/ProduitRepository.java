package com.AES.project.disponibiliteEtablissementSercives.repository;

import com.AES.project.disponibiliteEtablissementSercives.molel.Produit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    Optional<Produit> findById(Long id);


    ///**** verifier si le nom du produit existe deja ******
    Boolean existsByNomProduit(String nomProduit);


}
