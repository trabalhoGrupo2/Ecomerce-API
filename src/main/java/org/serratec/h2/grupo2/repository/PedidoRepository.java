package org.serratec.h2.grupo2.repository;


import java.util.List;

import org.serratec.h2.grupo2.domain.Pedido;
import org.serratec.h2.grupo2.domain.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    // Busca todos os pedidos feitos por um cliente específico
    List<Pedido> findByCliente(Cliente cliente);
}


