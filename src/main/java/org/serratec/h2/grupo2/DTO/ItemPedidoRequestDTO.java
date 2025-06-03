package org.serratec.h2.grupo2.DTO;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO que representa um item do pedido dentro do PedidoRequestDTO.
 */
public class ItemPedidoRequestDTO {

    // Swagger: descrição + exemplo + required = true
    @NotNull(message = "O ID do produto é obrigatório")
    @Schema(
        description = "Identificador único do produto no pedido", // aparece no Swagger UI
        example = "1",                                             // exemplo visível na doc
        required = true                                            // campo obrigatório
    )
    private Long produtoId;

    //Swagger: descrição + exemplo + required = true
    @NotNull(message = "A quantidade é obrigatória")
    @Schema(
        description = "Quantidade do produto para este item do pedido", 
        example = "3", 
        required = true
    )
    private Integer quantidade;

    //Swagger: descrição + exemplo (campo opcional, sem required)
    @Schema(
        description = "Desconto aplicado ao item do pedido (valor absoluto em reais)", 
        example = "10.00"
    )
    private BigDecimal desconto = BigDecimal.ZERO;

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

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }
}

