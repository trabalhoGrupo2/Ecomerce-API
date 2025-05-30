package org.serratec.h2.grupo2.service;

import java.util.List;
import java.util.Optional;

import org.serratec.h2.grupo2.DTO.PedidoDTO;
import org.serratec.h2.grupo2.domain.Pedido;
import org.serratec.h2.grupo2.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service  // Marca essa classe como serviço do Spring, responsável pela lógica de negócio
public class PedidoService {

    @Autowired
    private PedidoRepository repository;  // Repositório para acessar dados de Pedido no banco

    @Autowired
    private ClienteRepository clienteRepository;  // Repositório para acessar dados de Cliente

    @Autowired
    private ProdutoRepository produtoRepository;  // Repositório para acessar dados de Produto

    // Método para criar um novo pedido
    public Pedido criarPedido(PedidoRequestDTO pedidoDTO) {
        Pedido pedido = new Pedido();  // Cria uma nova instância da entidade Pedido

        // Busca o cliente pelo ID informado no DTO; lança exceção se não encontrar
        Cliente cliente = clienteRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        pedido.setCliente(cliente);  // Define o cliente do pedido
        pedido.setDataCriacao(LocalDate.now());  // Define a data atual como data de criação do pedido
        pedido.setStatus(StatusPedido.EM_PROCESSAMENTO);  // Inicializa o status do pedido

        // Para cada item recebido no DTO, cria e adiciona o item ao pedido
        for (ItemPedidoDTO itemDTO : pedidoDTO.getItens()) {
            // Busca o produto pelo ID informado no itemDTO; lança exceção se não encontrar
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            ItemPedido item = new ItemPedido();  // Cria um novo item para o pedido
            item.setProduto(produto);  // Define o produto do item
            item.setQuantidade(itemDTO.getQuantidade());  // Define a quantidade
            item.setPrecoUnitario(produto.getPreco());  // Define o preço unitário atual do produto
            item.calcularTotal();  // Calcula o preço total do item (quantidade * preço unitário)

            pedido.adicionarItem(item);  // Adiciona o item ao pedido (cuida do relacionamento)
        }

        return repository.save(pedido);  // Salva o pedido com os itens no banco e retorna o objeto salvo
    }

    // Método para alterar o status de um pedido pelo ID
    public Pedido alterarStatus(Long id, String status) {
        // Busca o pedido pelo ID; lança exceção se não encontrar
        Pedido pedido = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));

        // Converte a string status para o enum StatusPedido, ignorando maiúsculas/minúsculas
        pedido.setStatus(StatusPedido.valueOf(status.toUpperCase()));

        return repository.save(pedido);  // Salva o pedido com o status atualizado e retorna ele
    }

    // Método para buscar um pedido pelo ID
    public Pedido buscarPorId(Long id) {
        // Retorna o pedido encontrado ou lança exceção se não existir
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    // Método para listar todos os pedidos cadastrados
    public List<Pedido> listarTodos() {
        return repository.findAll();  // Retorna a lista de todos os pedidos no banco
    }

    // Método para editar um pedido existente
    public Pedido editarPedido(Long id, PedidoRequestDTO pedidoDTO) {
        Pedido pedido = buscarPorId(id);  // Busca o pedido pelo ID; lança exceção se não existir

        // Validação: se o pedido já foi pago, não permite edição e lança exceção
        if (pedido.getStatus() == StatusPedido.PAGO) {
            throw new RuntimeException("Pedido já foi pago e não pode ser alterado");
        }

        // Atualiza o cliente do pedido, buscando pelo ID enviado no DTO
        pedido.setCliente(clienteRepository.findById(pedidoDTO.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado")));

        pedido.getItens().clear();  // Limpa os itens atuais do pedido para atualizar

        // Para cada item do DTO, cria um novo ItemPedido e adiciona no pedido
        for (ItemPedidoDTO itemDTO : pedidoDTO.getItens()) {
            Produto produto = produtoRepository.findById(itemDTO.getProdutoId())
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

            ItemPedido item = new ItemPedido();
            item.setProduto(produto);
            item.setQuantidade(itemDTO.getQuantidade());
            item.setPrecoUnitario(produto.getPreco());
            item.calcularTotal();

            pedido.adicionarItem(item);  // Adiciona o item ao pedido
        }

        return repository.save(pedido);  // Salva o pedido atualizado no banco e retorna ele
    }

    // Método para remover um pedido pelo ID
    public void remover(Long id) {
        Pedido pedido = buscarPorId(id);  // Busca o pedido; lança exceção se não existir
        repository.delete(pedido);  // Remove o pedido do banco
    }
    
    public List<Pedido> listarPorClienteId(Long clienteId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        return repository.findByCliente(cliente);
    }
}

