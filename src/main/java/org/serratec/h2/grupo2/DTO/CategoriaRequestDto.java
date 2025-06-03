package org.serratec.h2.grupo2.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

public class CategoriaRequestDto {

    // Swagger, Nome da categoria (obrigatório, máximo de 100 caracteres)
    @NotBlank(message = "O nome da categoria é obrigatório")
    @Size(max = 100, message = "O nome da categoria deve ter no máximo 100 caracteres")
    @Schema(description = "Nome da categoria", example = "Eletrônicos", required = true)
    private String nome;

    public CategoriaRequestDto() {}

    public CategoriaRequestDto(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
