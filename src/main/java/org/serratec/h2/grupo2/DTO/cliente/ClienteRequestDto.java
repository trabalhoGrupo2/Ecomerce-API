package org.serratec.h2.grupo2.DTO.cliente;

import java.time.LocalDate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDto {

    // Swagger, Nome completo do cliente (obrigatório)
    @NotBlank
    @Schema(description = "Nome completo do cliente", example = "Maria Souza", required = true)
    private String nome;

    // Swagger, CPF do cliente no formato xxx.xxx.xxx-xx (obrigatório)
    @NotBlank
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Formato inválido, esperado xxx.xxx.xxx-xx")
    @Schema(description = "CPF do cliente no formato xxx.xxx.xxx-xx", example = "123.456.789-00", required = true)
    private String cpf;

    // Swagger, Data de nascimento do cliente (obrigatório)
    @NotNull(message = "A data de nascimento deve ser preenchido")
    @Schema(description = "Data de nascimento do cliente", example = "1990-05-20", required = true)
    private LocalDate dataDeNascimento;

    // Swagger, Telefone no formato (XX) 9XXXX-XXXX
    @Pattern(regexp = "^\\(\\d{2}\\) ?9?\\d{4}-\\d{4}$", message = "Telefone inválido, formato esperado: (XX) 9XXXX-XXXX")
    @Schema(description = "Telefone no formato (XX) 9XXXX-XXXX", example = "(21) 99876-5432")
    private String telefone;

    // Swagger, CEP no formato 99999-999
    @Pattern(regexp = "^\\d{5}-\\d{3}$", message = "CEP inválido. Use: 99999-999")
    @Schema(description = "CEP no formato 99999-999", example = "25000-000")
    private String cep;

    // Swagger, Número residencial, positivo ou 'S/N'
    @Pattern(regexp = "^[1-9]\\d*$|(?i)^s/?n$", message = "Número residencial deve ser um número positivo ou 'S/N'")
    @Schema(description = "Número residencial, positivo ou 'S/N'", example = "123")
    private String numero;

    // Swagger, Email válido para dados da conta
    @Email(message = "Email inválido")
    @Schema(description = "Email válido para login e contato", example = "maria@email.com")
    private String email;

    // Swagger, Senha com regras de complexidade
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$", 
        message = "A senha deve ter no mínimo 8 caracteres, incluindo letra maiúscula, número e caractere especial.")
    @Schema(description = "Senha com pelo menos 8 caracteres, contendo letra maiúscula, número e caractere especial", example = "Senha@123")
    private String senha;
}
