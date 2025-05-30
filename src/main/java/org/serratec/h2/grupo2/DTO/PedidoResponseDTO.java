package org.serratec.h2.grupo2.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO para enviar os dados completos do pedido para o cliente.
 */
public class PedidoResponseDTO {

    private Long id;                      // ID do pedido
    private ClienteResumoDTO cliente;     // Dados resumidos do cliente
    private List<ItemPedidoResponseDTO> itens;  // Lista dos itens do pedido
    private String status;                // Status do pedido (ex: PAGO, ENVIADO)
    private LocalDate dataCriacao;        // Data de criação do pedido
    private BigDecimal valorTotal;        // Valor total do pedido (somatório dos itens)

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClienteResumoDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteResumoDTO cliente) {
        this.cliente = cliente;
    }

    public List<ItemPedidoResponseDTO> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoResponseDTO> itens) {
        this.itens = itens;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }
}
