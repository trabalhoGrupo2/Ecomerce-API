package org.serratec.h2.grupo2.DTO.conta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ContaRequestDto {

    // Swagger, Email para login
    @Schema(description = "Email do usuário para login", example = "usuario@email.com", required = true)
    private String email;

    // Swagger, Senha para login
    @Schema(description = "Senha do usuário para login", example = "Senha@123", required = true)
    private String senha;

}
