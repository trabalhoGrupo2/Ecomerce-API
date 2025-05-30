package org.serratec.h2.grupo2.service;

import java.time.LocalDate;
import java.util.List;

import org.serratec.h2.grupo2.DTO.ItemPedidoRequestDTO;
import org.serratec.h2.grupo2.DTO.PedidoRequestDTO;
import org.serratec.h2.grupo2.DTO.PedidoStatusUpdateDTO;
import org.serratec.h2.grupo2.domain.Cliente;
import org.serratec.h2.grupo2.domain.ItemPedido;
import org.serratec.h2.grupo2.domain.Pedido;
import org.serratec.h2.grupo2.domain.Produto;
import org.serratec.h2.grupo2.enuns.StatusPedido;
import org.serratec.h2.grupo2.repository.ClienteRepository;
import org.serratec.h2.grupo2.repository.PedidoRepository;
import org.serratec.h2.grupo2.repository.ProdutoRepository;
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

    // Criar novo pedido
    public Pedido criarPedido(PedidoRequestDTO pedidoDTO) {
        Pedido pedido = new Pedido();

        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        pedido.setCliente(cliente);
        pedido.setDataCriacao(LocalDate.now());
        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);

        for (ItemPedidoRequestDTO itemDTO : pedidoDTO.getItens()) {
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

    // Alterar status usando DTO específico para atualização de status
    public Pedido alterarStatus(Long id, PedidoStatusUpdateDTO statusUpdateDTO) {
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        // Atualiza o status convertendo string para enum (usar método do enum para evitar erro)
        StatusPedido novoStatus = StatusPedido.statusPeloNome(statusUpdateDTO.getStatus());
        pedido.setStatus(novoStatus);

        return repository.save(pedido);
    }

    // Buscar pedido pelo ID
    public Pedido buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    // Listar todos os pedidos
    public List<Pedido> listarTodos() {
        return repository.findAll();
    }

    // Editar pedido com restrição para pedido pago
    public Pedido editarPedido(Long id, PedidoRequestDTO pedidoDTO) {
        Pedido pedido = buscarPorId(id);

        // Bloqueia edição se pedido já foi pago (ou status diferente de aguardando pagamento)
        if (pedido.getStatus() != StatusPedido.AGUARDANDO_PAGAMENTO) {
            throw new RuntimeException("Pedido não pode ser alterado após o pagamento");
        }

        pedido.setCliente(clienteRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado")));

        pedido.getItens().clear();

        for (ItemPedidoRequestDTO itemDTO : pedidoDTO.getItens()) {
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

    // Remover pedido
    public void remover(Long id) {
        Pedido pedido = buscarPorId(id);
        repository.delete(pedido);
    }

    // Listar pedidos por cliente
    public List<Pedido> listarPorClienteId(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return repository.findByCliente(cliente);
    }
}
