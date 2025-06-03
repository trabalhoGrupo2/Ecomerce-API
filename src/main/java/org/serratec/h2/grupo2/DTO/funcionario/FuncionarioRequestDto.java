package org.serratec.h2.grupo2.DTO.funcionario;

import java.time.LocalDate;
import org.serratec.h2.grupo2.enuns.Cargo;
import org.serratec.h2.grupo2.enuns.Genero;
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
public class FuncionarioRequestDto {

    // Swagger, Nome completo do funcionário (obrigatório)
    @NotBlank
    @Schema(description = "Nome completo do funcionário", example = "Carlos Alberto", required = true)
    private String nome;

    // Swagger, CPF no formato xxx.xxx.xxx-xx (obrigatório)
    @NotBlank
    @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Formato inválido, esperado xxx.xxx.xxx-xx")
    @Schema(description = "CPF no formato xxx.xxx.xxx-xx", example = "123.456.789-00", required = true)
    private String cpf;

    // Swagger, Data de nascimento (obrigatório)
    @NotNull(message = "A data de nascimento deve ser preenchido")
    @Schema(description = "Data de nascimento do funcionário", example = "1980-07-15", required = true)
    private LocalDate dataDeNascimento;

    // Swagger, Gênero do funcionário (obrigatório)
    @NotNull(message = "O gênero deve ser preenchido")
    @Schema(description = "Gênero do funcionário", example = "MASCULINO", required = true)
    private Genero genero;

    // Swagger, Telefone no formato (XX) 9XXXX-XXXX
    @Pattern(regexp = "^\\(\\d{2}\\) ?9?\\d{4}-\\d{4}$", message = "Telefone inválido, formato esperado: (XX) 9XXXX-XXXX")
    @Schema(description = "Telefone no formato (XX) 9XXXX-XXXX", example = "(21) 98765-4321")
    private String telefone;

    // Swagger, Cargo do funcionário (obrigatório)
    @NotNull(message = "O cargo deve ser preenchido")
    @Schema(description = "Cargo do funcionário", example = "ANALISTA", required = true)
    private Cargo cargo;

    // Swagger, Email válido para login
    @Email(message = "Email inválido")
    @Schema(description = "Email para login", example = "carlos@email.com")
    private String email;

    // Swagger, Senha com regras de complexidade
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-zA-Z])(?=.*\\d)(?=.*[^a-zA-Z0-9]).{8,}$", 
        message = "A senha deve ter no mínimo 8 caracteres, incluindo letra maiúscula, número e caractere especial.")
    @Schema(description = "Senha com pelo menos 8 caracteres, incluindo letra maiúscula, número e caractere especial", example = "Senha@123")
    private String senha;
}
