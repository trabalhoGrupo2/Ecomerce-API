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
import org.serratec.h2.grupo2.repository.CodigoDescontoRepository;
import org.serratec.h2.grupo2.repository.PedidoRepository;
import org.serratec.h2.grupo2.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// Swagger/OpenAPI imports
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Service
@Tag(name = "Pedido Service", description = "Serviço para gerenciamento de pedidos")
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CodigoDescontoRepository codigoDescontoRepository;

    @Operation(summary = "Criar pedido", description = "Cria um novo pedido com os dados fornecidos e aplica desconto se código válido")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para criação do pedido")
    })
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

        // Aplicar desconto do código se existir e estiver ativo
        BigDecimal desconto = BigDecimal.ZERO;
        if (pedidoDTO.getCodigoDesconto() != null && !pedidoDTO.getCodigoDesconto().isEmpty()) {
            var codigoOpt = codigoDescontoRepository.findByCodigo(pedidoDTO.getCodigoDesconto());
            if (codigoOpt.isPresent()) {
                var codigo = codigoOpt.get();
                if (codigo.isAtivo()) {
                    desconto = codigo.getValorDesconto();
                    pedido.setCodigoDesconto(codigo.getCodigo());
                }
            }
        }

        BigDecimal valorFinal = total.subtract(desconto);
        if (valorFinal.compareTo(BigDecimal.ZERO) < 0) {
            valorFinal = BigDecimal.ZERO;
        }
        pedido.setValorFinal(valorFinal);

        pedido.setValorFrete(pedidoDTO.getValorFrete());

        return pedidoRepository.save(pedido);
    }

    @Operation(summary = "Buscar pedido por ID", description = "Retorna um pedido existente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    @Operation(summary = "Listar todos os pedidos", description = "Retorna todos os pedidos cadastrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    })
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAll();
    }

    @Operation(summary = "Editar pedido", description = "Atualiza dados de um pedido existente e aplica desconto se código válido")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public Pedido editarPedido(Long id, PedidoRequestDTO dto) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        pedido.setCliente(cliente);
        pedido.setDataCriacao(dto.getDataCriacao() != null ? dto.getDataCriacao() : pedido.getDataCriacao());
        pedido.setStatus(dto.getStatus());

        BigDecimal total = pedido.getValorTotal();
        BigDecimal desconto = BigDecimal.ZERO;
        if (dto.getCodigoDesconto() != null && !dto.getCodigoDesconto().isEmpty()) {
            var codigoOpt = codigoDescontoRepository.findByCodigo(dto.getCodigoDesconto());
            if (codigoOpt.isPresent()) {
                var codigo = codigoOpt.get();
                if (codigo.isAtivo()) {
                    desconto = codigo.getValorDesconto();
                    pedido.setCodigoDesconto(codigo.getCodigo());
                }
            }
        }
        BigDecimal valorFinal = total.subtract(desconto);
        if (valorFinal.compareTo(BigDecimal.ZERO) < 0) {
            valorFinal = BigDecimal.ZERO;
        }
        pedido.setValorFinal(valorFinal);

        pedido.setValorFrete(dto.getValorFrete());

        return pedidoRepository.save(pedido);
    }

    @Operation(summary = "Alterar status do pedido", description = "Atualiza o status do pedido")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
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

    @Operation(summary = "Listar pedidos por cliente", description = "Retorna todos os pedidos feitos por um cliente específico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedidos retornados com sucesso"),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public List<Pedido> listarPorClienteId(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        return pedidoRepository.findByCliente(cliente);
    }

    @Operation(summary = "Calcular frete", description = "Calcula o frete fixo para um pedido")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Frete calculado com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    public Pedido calcularFrete(Long id, Double distanciaIgnorada) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        pedido.setValorFrete(BigDecimal.valueOf(20.0));
        return pedidoRepository.save(pedido);
    }
}
