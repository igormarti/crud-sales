package io.github.igormarti.service.impl;

import io.github.igormarti.exception.PasswordIncorretoException;
import io.github.igormarti.rest.dto.CredenciaisDTO;
import io.github.igormarti.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioServiceImpl usuarioService;

    @Override
    public UserDetails autenticar(CredenciaisDTO dto) {
        UserDetails userDetails = usuarioService.loadUserByUsername(dto.getUsername());

        boolean userMatch = passwordEncoder.matches(dto.getPassword(), userDetails.getPassword());

        if(!userMatch){
            throw new PasswordIncorretoException("Senha incorreta.");
        }

        return userDetails;
    }
}
