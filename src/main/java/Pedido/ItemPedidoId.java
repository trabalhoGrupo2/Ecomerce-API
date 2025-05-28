package Pedido;

import java.io.Serializable;

import jakarta.persistence.Embeddable;

@Embeddable
public class ItemPedidoId implements Serializable {

    private Long pedidoId;
    private Long produtoId;


}