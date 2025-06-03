package org.serratec.h2.grupo2.DTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.time.LocalDate;
import org.serratec.h2.grupo2.enuns.StatusPedido;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO usado para receber os dados de criação ou edição de um pedido.
 */
public class PedidoRequestDTO {
    
    @Schema(description = "Data de criação do pedido", example = "2023-06-10")
    private LocalDate dataCriacao;

    @Schema(description = "Status do pedido", example = "PENDENTE")
    private StatusPedido status;

    @Schema(description = "Valor do frete associado ao pedido", example = "50.00")
    private BigDecimal valorFrete;  // Alterado para BigDecimal

    @NotNull(message = "O ID do cliente é obrigatório")
    @Schema(description = "ID do cliente que está realizando o pedido", example = "123", required = true)
    private Long clienteId;

    @NotEmpty(message = "O pedido deve conter ao menos um item")
    @Schema(description = "Lista de itens do pedido", required = true)
    private List<ItemPedidoRequestDTO> itens;

    @Schema(description = "Código de desconto aplicado ao pedido", example = "DESCONTO10", nullable = true)
    private String codigoDesconto;

    // Getters e Setters

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public List<ItemPedidoRequestDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoRequestDTO> itens) {
        this.itens = itens;
    }

    public String getCodigoDesconto() {
        return codigoDesconto;
    }

    public void setCodigoDesconto(String codigoDesconto) {
        this.codigoDesconto = codigoDesconto;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public StatusPedido getStatus() {
        return status;
    }

    public void setStatus(StatusPedido status) {
        this.status = status;
    }

    public BigDecimal getValorFrete() {
        return valorFrete;
    }

    public void setValorFrete(BigDecimal valorFrete) {
        this.valorFrete = valorFrete;
    }
}
