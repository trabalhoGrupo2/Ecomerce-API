package org.serratec.h2.grupo2.DTO;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

public class ItemPedidoResponseDTO {

    @Schema(description = "ID do item do pedido", example = "1")
    private Long id;

    @Schema(description = "ID do produto", example = "10")
    private Long produtoId;

    @Schema(description = "Nome do produto", example = "Camiseta Azul")
    private String nomeProduto;

    @Schema(description = "Quantidade do produto no item do pedido", example = "3")
    private Integer quantidade;

    @Schema(description = "Preço unitário do produto", example = "50.00")
    private BigDecimal precoUnitario;

    @Schema(description = "Desconto aplicado no item do pedido", example = "10.00")
    private BigDecimal desconto;

    @Schema(description = "Preço total do item (quantidade * preço unitário - desconto)", example = "140.00")
    private BigDecimal precoTotal;

    // Construtores
    public ItemPedidoResponseDTO() {}

    public ItemPedidoResponseDTO(Long id, Long produtoId, String nomeProduto, Integer quantidade, BigDecimal precoUnitario, BigDecimal desconto, BigDecimal precoTotal) {
        this.id = id;
        this.produtoId = produtoId;
        this.nomeProduto = nomeProduto;
        this.quantidade = quantidade;
        this.precoUnitario = precoUnitario;
        this.desconto = desconto;
        this.precoTotal = precoTotal;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(Long produtoId) {
        this.produtoId = produtoId;
    }

    public String getNomeProduto() {
        return nomeProduto;
    }

    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getDesconto() {
        return desconto;
    }

    public void setDesconto(BigDecimal desconto) {
        this.desconto = desconto;
    }

    public BigDecimal getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(BigDecimal precoTotal) {
        this.precoTotal = precoTotal;
    }
}
