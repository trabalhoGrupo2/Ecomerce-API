package org.serratec.h2.grupo2.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.serratec.h2.grupo2.enuns.StatusPedido;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valorFrete; // BigDecimal para frete

    private LocalDate dataCriacao;

    @Enumerated(EnumType.STRING)
    private StatusPedido status;

    @ManyToOne(optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<ItemPedido> itens = new ArrayList<>();

    private String codigoDesconto;  // campo para salvar o cupom

    private BigDecimal valorFinal;

    public void adicionarItem(ItemPedido item) {
        item.setPedido(this);
        this.itens.add(item);
    }

    public void removerItem(ItemPedido item) {
        this.itens.remove(item);
        item.setPedido(null);
    }

    public BigDecimal getValorTotal() {
        return itens.stream()
            .map(ItemPedido::getPrecoTotal)
            .filter(valor -> valor != null)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
