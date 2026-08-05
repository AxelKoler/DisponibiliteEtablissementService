package com.AES.project.disponibiliteEtablissementSercives.services;

import com.AES.project.disponibiliteEtablissementSercives.dto.UserDtoRequest;
import com.AES.project.disponibiliteEtablissementSercives.dto.UserDtoResponse;
import com.AES.project.disponibiliteEtablissementSercives.molel.Etablissement;
import com.AES.project.disponibiliteEtablissementSercives.molel.Role;
import com.AES.project.disponibiliteEtablissementSercives.molel.User;
import com.AES.project.disponibiliteEtablissementSercives.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    ///*************transfoemer un user en user response********

    private UserDtoResponse transformDto(User user){

        UserDtoResponse userResponse= UserDtoResponse.builder()
                .id(user.getId())
                .nomUtilisateur(user.getNomUtilisateur())
                .role(user.getRole())
                .mail(user.getMail())
                .numero(user.getTelephone())
                .pays(user.getPays())
                .ville(user.getVille())
                .adresse(user.getAdresse())
                .dateCreation(user.getDateCreation())
                .createur(user.getCreateur())
                .etablissementTravail(user.getEtablissementTravail())
                .build();

        return userResponse;
    }



    ///****Creation de user Gerant**************************************
    public UserDtoResponse creerUserGerant(UserDtoRequest userdtorequest){
        if(userRepository.existsByNomUtilisateur(userdtorequest.getNom())){
            throw new RuntimeException("user existe deja");
        }

        if(userRepository.existsByMail(userdtorequest.getMail())){
            throw new RuntimeException("user existe deja");
        }
    //***********Forcer le role de 'lutilisateur en gerant*****
        userdtorequest.setRole(Role.GERANT);
        //***********transformer le userRequest en user pour le save*****
        User user=new User();

        user.setNomUtilisateur(userdtorequest.getNom());
        user.setPassword(passwordEncoder.encode(userdtorequest.getPassword()));
        //user.setPassword(userdtorequest.getPassword());
        user.setRole(userdtorequest.getRole());
        user.setMail(userdtorequest.getMail());
        user.setTelephone(userdtorequest.getTelephone());
        user.setVille(userdtorequest.getVille());
        user.setPays(userdtorequest.getPays());
        user.setAdresse(userdtorequest.getAdresse());
        user.setCreateur(userdtorequest.getCreateur());
        user.setEtablissementTravail(userdtorequest.getEtablissementTravail());
        //************save user*******************************************
        userRepository.save(user);

        //*****creer UserDtoResponse grace au builde
       UserDtoResponse userResponse=transformDto(user);

        return userResponse;
    }




    ///****Creation de user Client**************************************
    public UserDtoResponse creerUserClient(UserDtoRequest userdtorequest){

        if(userRepository.existsByNomUtilisateur(userdtorequest.getNom())){
            throw new RuntimeException("Gerant existe deja");
        }
        if(userRepository.existsByMail(userdtorequest.getMail())){
            throw new RuntimeException("user existe deja");
        }
        userdtorequest.setRole(Role.CLIENT);

        //***********transformer le userRequest en user pour le save*****
        User user=new User();

        user.setNomUtilisateur(userdtorequest.getNom());
        user.setPassword(passwordEncoder.encode(userdtorequest.getPassword()));
        //user.setPassword(userdtorequest.getPassword());
        user.setMail(userdtorequest.getMail());
        user.setTelephone(userdtorequest.getTelephone());
        user.setRole(userdtorequest.getRole());
        user.setVille(userdtorequest.getVille());
        user.setPays(userdtorequest.getPays());
        user.setAdresse(userdtorequest.getAdresse());
        //************save user*******************************************
        userRepository.save(user);

        //*****creer UserDtoResponse grace au builde
        UserDtoResponse userResponse=transformDto(user);

        return userResponse;
    }




    ///****Creation de user Client**************************************
    public UserDtoResponse creerUserAdmin(UserDtoRequest userdtorequest){
        if(userRepository.existsByNomUtilisateur(userdtorequest.getNom())){
            throw new RuntimeException("Admin existe deja");
        }
        if(userRepository.existsByMail(userdtorequest.getMail())){
            throw new RuntimeException("user existe deja");
        }
        userdtorequest.setRole(Role.ADMIN);

        ///***********transformer le userRequest en user pour le save*****
        User user=new User();

        user.setNomUtilisateur(userdtorequest.getNom());
        user.setPassword(passwordEncoder.encode(userdtorequest.getPassword()));
        //user.setPassword(userdtorequest.getPassword());
        user.setMail(userdtorequest.getMail());
        user.setTelephone(userdtorequest.getTelephone());
        user.setRole(userdtorequest.getRole());

        ///************save user*******************************************
        userRepository.save(user);

        ///*****creer UserDtoResponse grace au builde
        UserDtoResponse userResponse=transformDto(user);

        return userResponse;
    }



    ///****************Lister tous les utilisateurs************************
    public List<UserDtoResponse> tousLesUsers(){

        List<User> listUser=userRepository.findAll();

        //transformer tous les user en userResponse
        List<UserDtoResponse> listUserResponse= listUser.stream()
                .map(this::transformDto)
                .toList();

        return listUserResponse;
    }



    ///***************Les user d'un etablissement***************************


    ///*****Suppression de user***************************************************
    @Transactional
    public String supprimerUser(Long id){

        User user=userRepository.findById(id)
                .orElseThrow(()->new UsernameNotFoundException("User introuvable: "+id));
        userRepository.delete(user);


        return "user "+id+" supprimé avec succes";
    }
}
