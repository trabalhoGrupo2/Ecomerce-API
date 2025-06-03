package org.serratec.h2.grupo2.DTO.funcionario;

import java.time.LocalDate;

import org.serratec.h2.grupo2.enuns.Cargo;
import org.serratec.h2.grupo2.enuns.Genero;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioResponseDto {

    // Swagger, Identificador único do funcionário
    @Schema(description = "Identificador único do funcionário", example = "45")
    private Long id;

    // Swagger, Nome completo do funcionário
    @Schema(description = "Nome completo do funcionário", example = "Carlos Alberto")
    private String nome;

    // Swagger, CPF do funcionário no formato xxx.xxx.xxx-xx
    @Schema(description = "CPF do funcionário", example = "123.456.789-00")
    private String cpf;

    // Swagger, Data de nascimento do funcionário
    @Schema(description = "Data de nascimento do funcionário", example = "1980-07-15")
    private LocalDate dataDeNascimento;

    // Swagger, Cargo do funcionário
    @Schema(description = "Cargo do funcionário", example = "ANALISTA")
    private Cargo cargo;

    // Swagger, Gênero do funcionário
    @Schema(description = "Gênero do funcionário", example = "MASCULINO")
    private Genero genero;

    // Swagger, Telefone do funcionário
    @Schema(description = "Telefone de contato do funcionário", example = "(21) 98765-4321")
    private String telefone;

    // Swagger, Email do funcionário
    @Schema(description = "Email de contato do funcionário", example = "carlos@email.com")
    private String email;
}
