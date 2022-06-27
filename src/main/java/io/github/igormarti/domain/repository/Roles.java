package io.github.igormarti.domain.repository;

import io.github.igormarti.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface Roles extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
