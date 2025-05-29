package org.serratec.h2.grupo2.DTO;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class ItemPedidoDTO {

    @NotNull(message = "O ID do produto é obrigatório")
    private Long produtoId;

    @Min(value = 1, message = "A quantidade deve ser no mínimo 1")
    private Integer quantidade;

    // Getters e Setters
    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }
}