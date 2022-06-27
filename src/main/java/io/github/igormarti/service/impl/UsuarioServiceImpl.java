package io.github.igormarti.service.impl;

import io.github.igormarti.domain.entity.Role;
import io.github.igormarti.domain.entity.Usuario;
import io.github.igormarti.domain.repository.Roles;
import io.github.igormarti.domain.repository.Usuarios;
import io.github.igormarti.exception.NaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UserDetailsService {

    private final Usuarios repository;
    private final PasswordEncoder passwordEncoder;

    private final Roles rolesRepository;

    public void save(Usuario usuario){

        Role role = rolesRepository.findByName("ROLE_USER")
                        .orElseThrow(() -> new NaoEncontradoException("Sistema sem papéis."));

        usuario.setRoles(Arrays.asList(role));
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        repository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       Usuario usuario = repository.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario não encontrado."));

       return usuario;
    }
}
