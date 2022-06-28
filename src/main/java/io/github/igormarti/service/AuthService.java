package io.github.igormarti.service;

import io.github.igormarti.rest.dto.CredenciaisDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {
    UserDetails autenticar(CredenciaisDTO dto);
}
