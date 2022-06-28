package io.github.igormarti.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDTO {

    private String token;
    private String login;
    private Long expiration;
    private Collection<? extends GrantedAuthority> authorities;

}
