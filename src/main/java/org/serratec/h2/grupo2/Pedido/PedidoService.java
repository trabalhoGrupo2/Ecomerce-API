package org.serratec.h2.grupo2.Pedido;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {
    
    @Autowired
    private PedidoRepository repository;

    // Altera status
    public Pedido alterarStatus(Long id, StatusPedido status) {
        Optional<Pedido> pedidoExiste = repository.findById(id);
        if (pedidoExiste.isPresent()) {
            Pedido pedido = pedidoExiste.get();
            pedido.setStatus(status); 
            return repository.save(pedido); 
        }
        return null;
    }

    // Busca pedido  ID
    public Pedido buscarPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    // Lista  pedidos
    public List<Pedido> listarTodos() {
        return repository.findAll();
    }

    // Cria pedido baseado no DTO
    public Pedido criarPedido(PedidoDTO pedidoDTO) {
        Pedido novoPedido = new Pedido();
        novoPedido.setCliente(pedidoDTO.getCliente());
        novoPedido.setProdutos(pedidoDTO.getProdutos());
        novoPedido.setStatus(StatusPedido.PAGO); 
        return repository.save(novoPedido);
    }

    // Edita  pedido 
    public Pedido editarPedido(Long id, PedidoDTO pedidoDTO) {
        Optional<Pedido> pedidoExiste = repository.findById(id);
        if (pedidoExiste.isPresent()) {
            Pedido pedido = pedidoExiste.get();
            pedido.setCliente(pedidoDTO.getCliente());
            pedido.setProdutos(pedidoDTO.getProdutos());
            return repository.save(pedido);
        }
        return null;
    }
}