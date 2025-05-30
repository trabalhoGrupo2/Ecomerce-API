package org.serratec.h2.grupo2.service;

import java.util.List;
import java.util.Optional;

import org.serratec.h2.grupo2.DTO.PedidoDTO;
import org.serratec.h2.grupo2.domain.Pedido;
import org.serratec.h2.grupo2.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

   
    public Pedido buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido com ID " + id + " não encontrado."));
    }


    public List<Pedido> listarTodos() {
        return (List<Pedido>) repository.findAll();
    }

 
    public Pedido criarPedido(PedidoDTO pedidoDTO) {
        Pedido novoPedido = new Pedido();
        novoPedido.setDescricao(pedidoDTO.getDescricao());
        novoPedido.setValorTotal(pedidoDTO.getValorTotal());
        novoPedido.setStatus("PENDENTE");
        novoPedido.setValorFrete(0.0);
        return repository.save(novoPedido);
    }

    
    public Pedido editarPedido(Long id, PedidoDTO pedidoDTO) {
        Pedido pedidoExistente = buscarPorId(id);

        pedidoExistente.setDescricao(pedidoDTO.getDescricao());
        pedidoExistente.setValorTotal(pedidoDTO.getValorTotal());
        

        return repository.save(pedidoExistente);
    }


    public void remover(Long id) {
        if (!repository.existsById(id)) {
            throw new IllegalArgumentException("Pedido com ID " + id + " não encontrado.");
        }
        repository.deleteById(id);
    }


    public Pedido alterarStatus(Long id, String status) {
        Pedido pedido = buscarPorId(id);
        pedido.setStatus(status.toUpperCase());
        return repository.save(pedido);
    }

   
    public Pedido calcularFrete(Long id, Double distanciaKm) {
        if (distanciaKm == null || distanciaKm <= 0) {
            throw new IllegalArgumentException("Distância inválida para cálculo do frete.");
        }

        Pedido pedido = buscarPorId(id);
        Double valorFrete = distanciaKm * 0.50;

        pedido.setValorFrete(valorFrete);
        pedido.setValorTotal(pedido.getValorTotal() + valorFrete);

        return repository.save(pedido);
    }
}