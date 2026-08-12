package com.AES.project.disponibiliteEtablissementSercives.controler;

import com.AES.project.disponibiliteEtablissementSercives.dto.UserDtoRequest;
import com.AES.project.disponibiliteEtablissementSercives.dto.UserDtoResponse;
import com.AES.project.disponibiliteEtablissementSercives.molel.Role;
import com.AES.project.disponibiliteEtablissementSercives.molel.User;
import com.AES.project.disponibiliteEtablissementSercives.repository.UserRepository;
import com.AES.project.disponibiliteEtablissementSercives.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    //api pour créer Admin
    @PostMapping("/admin")
    public ResponseEntity<UserDtoResponse> makeUserAdmin(@RequestBody UserDtoRequest userRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.creerUserAdmin(userRequest));

    }


    //api pour créer user gerant
    @PostMapping("/gerant")
    public ResponseEntity<UserDtoResponse> makeUserGerant(@RequestBody UserDtoRequest userRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.creerUserGerant(userRequest));
    }

    //api pour créer user client
    @PostMapping
    public ResponseEntity<UserDtoResponse> makeUser(@RequestBody UserDtoRequest userRequest){

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.creerUserClient(userRequest));
    }


    ///****Lister tous les user(admin)****************************************************
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDtoResponse>> allUser(){
        return ResponseEntity.ok(userService.tousLesUsers());
    }

    ///desctiver compte (admin et user connecté)********************************************************

    /*@PostMapping
    @PreAuthorize("haseRole('ADMIN, GERANT'")")

    */

    ///**********Supprimer ********************************************


    @DeleteMapping("/{id}")
    @PreAuthorize("haseRol('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.supprimerUser(id);
        return ResponseEntity.noContent().build();
    }



}

