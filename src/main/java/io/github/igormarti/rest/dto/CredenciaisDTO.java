package io.github.igormarti.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class CredenciaisDTO {

    @NotEmpty(message = "{campo.login.obrigatorio}")
    private String username;
    @NotEmpty(message = "{campo.password.obrigatorio}")
    private String password;

}
