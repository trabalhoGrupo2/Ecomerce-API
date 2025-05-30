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

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Pedido criarPedido(PedidoRequestDTO pedidoDTO) {
        Pedido pedido = new Pedido();

        // Busca o cliente no banco pelo ID
        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        pedido.setCliente(cliente);
        pedido.setDataCriacao(LocalDate.now());
        pedido.setStatus(StatusPedido.EM_PROCESSAMENTO);

        // Para cada item recebido no DTO, cria o ItemPedido e adiciona ao pedido
        for (ItemPedidoDTO itemDTO : pedidoDTO.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());
            item.calcularTotal();

            pedido.adicionarItem(item);
        }

        // Salva o pedido com os itens no banco
        return repository.save(pedido);
    }

    // Implementações simplificadas dos outros métodos
    public Pedido alterarStatus(Long id, String status) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.setStatus(StatusPedido.valueOf(status.toUpperCase()));
        return repository.save(pedido);
    }

    public Pedido buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public List<Pedido> listarTodos() {
        return repository.findAll();
    }

    public Pedido editarPedido(Long id, PedidoRequestDTO pedidoDTO) {
        Pedido pedido = buscarPorId(id);
        pedido.setCliente(clienteRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado")));
        pedido.getItens().clear();

        for (ItemPedidoDTO itemDTO : pedidoDTO.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());
            item.calcularTotal();

            pedido.adicionarItem(item);
        }

        return repository.save(pedido);
    }

    public void remover(Long id) {
        Pedido pedido = buscarPorId(id);
        repository.delete(pedido);
    }
}
