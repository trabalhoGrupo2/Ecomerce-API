package org.serratec.h2.grupo2.repository;

import java.util.Optional;

import org.serratec.h2.grupo2.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,Long> {

	//Pedido findById(Long id);
}
