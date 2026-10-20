package com.AES.project.disponibiliteEtablissementSercives.repository;

import com.AES.project.disponibiliteEtablissementSercives.molel.Etablissement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EtablissementRepository extends JpaRepository<Etablissement,Long> {

   Optional <Etablissement> findById(Long id);
    
    // Vérifie si un restaurant avec ce nom existe déjà
    Boolean existsByNomEtablissement(String nomEtablissement);
}
