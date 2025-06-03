package org.serratec.h2.grupo2.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.serratec.h2.grupo2.DTO.cliente.ClienteResponseDto;  // import do DTO do cliente
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO para enviar os dados completos do pedido para o cliente.
 */
public class PedidoResponseDTO {

    // Swagger, ID do pedido
    @Schema(description = "ID do pedido", example = "1")
    private Long id;                     

    // Swagger, Dados do cliente
    @Schema(description = "Dados do cliente", required = true)
    private ClienteResponseDto cliente;    

    // Swagger, Lista de itens do pedido
    @Schema(description = "Lista dos itens do pedido", required = true)
    private List<ItemPedidoResponseDTO> itens;  // considere criar este DTO para ItemPedido

    // Swagger, Status do pedido
    @Schema(description = "Status do pedido (ex: PAGO, ENVIADO)", example = "PAGO")
    private String status;                

    // Swagger, Data de criação do pedido
    @Schema(description = "Data de criação do pedido", example = "2023-06-10")
    private LocalDate dataCriacao;        

    // Swagger, Valor total do pedido
    @Schema(description = "Valor total do pedido (somatório dos itens)", example = "250.00")
    private BigDecimal valorTotal;        

    // Swagger, Código de desconto
    @Schema(description = "Código de desconto aplicado ao pedido", example = "DESCONTO10")
    private String codigoDesconto;

    // Swagger, Valor final do pedido após desconto
    @Schema(description = "Valor final do pedido após desconto", example = "225.00")
    private BigDecimal valorFinal;

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClienteResponseDto getCliente() {
        return cliente;
    }

    public void setCliente(ClienteResponseDto cliente) {
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
    
    public String getCodigoDesconto() {
        return codigoDesconto;
    }

    public void setCodigoDesconto(String codigoDesconto) {
        this.codigoDesconto = codigoDesconto;
    }

    public BigDecimal getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(BigDecimal valorFinal) {
        this.valorFinal = valorFinal;
    }
}
