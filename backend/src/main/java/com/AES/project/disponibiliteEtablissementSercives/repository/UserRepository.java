package com.AES.project.disponibiliteEtablissementSercives.repository;

import com.AES.project.disponibiliteEtablissementSercives.molel.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    ////important pour authentification

    Optional<User> findByNomUtilisateur(String nomUtilisateur);

    //// Vérifier si un username existe déjà important pour nr pas avoir de doublon
        Boolean  existsByNomUtilisateur(String username);
        Boolean existsByMail(String mail);

        //boolean existsByMail(String mail);

}
