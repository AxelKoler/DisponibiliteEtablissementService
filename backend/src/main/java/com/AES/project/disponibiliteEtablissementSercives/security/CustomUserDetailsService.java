package com.AES.project.disponibiliteEtablissementSercives.security;

import com.AES.project.disponibiliteEtablissementSercives.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByNomUtilisateur(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getNomUtilisateur(),
                        user.getPassword(),
                        List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
                ))
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));
    }
}
