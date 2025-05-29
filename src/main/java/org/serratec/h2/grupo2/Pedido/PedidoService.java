package org.serratec.h2.grupo2.Pedido;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

public class PedidoService {
    
	@Autowired
	private PedidoRepository repository;
	
	public Pedido alterarStatus(Long id, String status) {
		Optional<Pedido> pedidoExiste = repository.findById(id);
		if( pedidoExiste.isPresent()) {
			Pedido pedido = pedidoExiste.get();
			
		}
		return null;
	}

	public Pedido buscarPorId(Long id) {
		
		return null;
	}

	public List<Pedido> listarTodos() {
		
		return null;
	}

	public Pedido criarPedido(PedidoDTO pedidoDTO) {
		
		return null;
	}

	public Pedido editarPedido(Long id, PedidoDTO pedidoDTO) {
		
		return null;
	}

	

}