package org.serratec.h2.grupo2.service;

import java.math.BigDecimal;
import java.time.LocalDate;
=======
import java.util.List;

>>>>>>> 07abc89ab2ae5ddf60df3088d0bcedf14977c4fb

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
import org.serratec.h2.grupo2.repository.CodigoDescontoRepository;
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
    
    @Autowired
    private CodigoDescontoRepository codigoDescontoRepository;

    // Criar novo pedido
    public Pedido criarPedido(PedidoRequestDTO pedidoDTO) {
        Pedido pedido = new Pedido();

        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        pedido.setCliente(cliente);
        pedido.setDataCriacao(LocalDate.now());
        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);

        // Adiciona itens ao pedido
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

        // Calcula o total dos itens
        BigDecimal totalItens = pedido.getItens().stream()
                .map(ItemPedido::getPrecoTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Aplica código de desconto, se fornecido
        String codigo = pedidoDTO.getCodigoDesconto();
        if (codigo != null && !codigo.isBlank()) {
            CodigoDesconto desconto = codigoDescontoRepository.findByCodigo(codigo)
                    .orElseThrow(() -> new RuntimeException("Código de desconto inválido"));

            if (!desconto.isAtivo()) {
                throw new RuntimeException("Código de desconto está inativo");
            }

            totalItens = totalItens.subtract(desconto.getValorDesconto());
            pedido.setCodigoDesconto(codigo);
        }

        // Garante que o valor final não seja negativo
        if (totalItens.compareTo(BigDecimal.ZERO) < 0) {
            totalItens = BigDecimal.ZERO;
        }

        pedido.setValorFinal(totalItens);

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

