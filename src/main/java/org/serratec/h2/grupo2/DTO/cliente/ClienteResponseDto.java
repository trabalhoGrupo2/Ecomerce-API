package org.serratec.h2.grupo2.DTO.cliente;

import java.time.LocalDate;

import org.serratec.h2.grupo2.domain.Endereco;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDto {

    // Swagger, Identificador único do cliente
    @Schema(description = "Identificador único do cliente", example = "123")
    private Long id;

    // Swagger, Nome completo do cliente
    @Schema(description = "Nome completo do cliente", example = "João da Silva")
    private String nome;

    // Swagger, CPF do cliente
    @Schema(description = "CPF do cliente", example = "123.456.789-00")
    private String cpf;

    // Swagger, Data de nascimento do cliente
    @Schema(description = "Data de nascimento do cliente", example = "1985-10-15")
    private LocalDate dataDeNascimento;

    // Swagger, Telefone de contato do cliente
    @Schema(description = "Telefone de contato do cliente", example = "(21) 99999-8888")
    private String telefone;

    // Swagger, Email do cliente
    @Schema(description = "Email do cliente", example = "joao.silva@email.com")
    private String email;

    // Swagger, Endereço do cliente
    @Schema(description = "Endereço do cliente")
    private Endereco endereco;
}
