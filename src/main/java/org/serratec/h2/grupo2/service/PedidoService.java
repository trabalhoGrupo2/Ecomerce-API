package org.serratec.h2.grupo2.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.serratec.h2.grupo2.DTO.ItemPedidoRequestDTO;
import org.serratec.h2.grupo2.DTO.PedidoRequestDTO;
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
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public Pedido criarPedido(PedidoRequestDTO pedidoDTO) {
        Pedido pedido = new Pedido();
        pedido.setDataCriacao(LocalDate.now());
        pedido.setStatus(StatusPedido.AGUARDANDO_PAGAMENTO);

        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        pedido.setCliente(cliente);

        BigDecimal total = BigDecimal.ZERO;

        for (ItemPedidoRequestDTO itemDTO : pedidoDTO.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());

            BigDecimal precoUsado = produto.getPrecoPromocional() != null
                    ? produto.getPrecoPromocional()
                    : produto.getPreco();
            item.setPrecoUnitario(precoUsado);

            item.setDesconto(itemDTO.getDesconto());

            item.calcularTotal();
            total = total.add(item.getPrecoTotal());

            item.setPedido(pedido);
            pedido.getItens().add(item);
        }

        pedido.setValorFinal(total);
        pedido.setValorFrete(pedidoDTO.getValorFrete());  // Salvar valorFrete recebido no DTO

        return pedidoRepository.save(pedido);
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }
    
    public Pedido editarPedido(Long id, PedidoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        pedido.setCliente(cliente);
        pedido.setDataCriacao(dto.getDataCriacao() != null ? dto.getDataCriacao() : pedido.getDataCriacao());
        pedido.setStatus(dto.getStatus());
        pedido.setValorFrete(dto.getValorFrete());

        return pedidoRepository.save(pedido);
    }
    
    public Pedido alterarStatus(Long id, String status) {
        Pedido pedido = pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        try {
            pedido.setStatus(StatusPedido.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Status inválido: " + status);
        }

        return pedidoRepository.save(pedido);
    }
    
    public List<Pedido> listarPorClienteId(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
            .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        return pedidoRepository.findByCliente(cliente);
    }
  
    public Pedido calcularFrete(Long id, Double distanciaIgnorada) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedido.setValorFrete(BigDecimal.valueOf(20.0)); // valor fixo do frete corrigido para BigDecimal
        return pedidoRepository.save(pedido);
    }
}
