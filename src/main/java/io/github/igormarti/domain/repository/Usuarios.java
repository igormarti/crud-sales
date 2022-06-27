package io.github.igormarti.domain.repository;

import io.github.igormarti.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Usuarios extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByLogin(String login);
}
