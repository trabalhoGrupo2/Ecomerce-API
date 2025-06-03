package org.serratec.h2.grupo2.DTO;

import io.swagger.v3.oas.annotations.media.Schema;

public class CategoriaResponseDto {

    // Swagger, Identificador único da categoria
    @Schema(description = "Identificador único da categoria", example = "1")
    private Long id;

    // Swagger, Nome da categoria
    @Schema(description = "Nome da categoria", example = "Eletrônicos")
    private String nome;

    public CategoriaResponseDto() {}

    public CategoriaResponseDto(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
