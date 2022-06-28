package io.github.igormarti.rest.controller;

import io.github.igormarti.domain.entity.Usuario;
import io.github.igormarti.exception.PasswordIncorretoException;
import io.github.igormarti.rest.dto.CredenciaisDTO;
import io.github.igormarti.rest.dto.TokenDTO;
import io.github.igormarti.service.AuthService;
import io.github.igormarti.service.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private JwtService jwtService;

    @PostMapping
    public ResponseEntity<TokenDTO> store(@RequestBody  @Valid  CredenciaisDTO dto){
        try{

            UserDetails usuario = authService.autenticar(dto);

            String token = jwtService.generateToken((Usuario) usuario);
            Long expiration = jwtService.getExpiration(token);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new TokenDTO(token, dto.getUsername(), expiration, usuario.getAuthorities() ));

        }catch (UsernameNotFoundException | PasswordIncorretoException ex){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        }
    }

}
