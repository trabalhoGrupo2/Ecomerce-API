package Pedido;

import java.math.BigDecimal;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;

@Entity
public class ItemPedido {

    @EmbeddedId
    private ItemPedidoId id = new ItemPedidoId();

    @ManyToOne
    @MapsId("pedidoId")
    private Pedido pedido;

    @ManyToOne
    @MapsId("produtoId")
    private Produto produto;

    private BigDecimal valor;
    private BigDecimal desconto;
    private int quantidade;

 
}