package org.serratec.h2.grupo2.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.serratec.h2.grupo2.DTO.ItemPedidoResponseDTO;
import org.serratec.h2.grupo2.DTO.cliente.ClienteResponseDto;
import org.serratec.h2.grupo2.DTO.PedidoResponseDTO;
import org.serratec.h2.grupo2.domain.ItemPedido;
import org.serratec.h2.grupo2.domain.Pedido;
import org.springframework.stereotype.Component;

@Component
public class PedidoMapper {

    // Converte entidade Pedido para PedidoResponseDTO // Swagger (método de conversão DTO)
    public PedidoResponseDTO toResponse(Pedido pedido) { // Swagger
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());

        // Mapear cliente para ClienteResponseDto
        ClienteResponseDto clienteDto = new ClienteResponseDto();
        clienteDto.setId(pedido.getCliente().getId());
        clienteDto.setNome(pedido.getCliente().getNome());
        clienteDto.setCpf(pedido.getCliente().getCpf());
        clienteDto.setDataDeNascimento(pedido.getCliente().getDataDeNascimento());
        clienteDto.setTelefone(pedido.getCliente().getTelefone());

        // Pegar email da conta com verificação de null para evitar erros
        if (pedido.getCliente().getConta() != null && pedido.getCliente().getConta().getEmail() != null) {
            clienteDto.setEmail(pedido.getCliente().getConta().getEmail());
        } else {
            clienteDto.setEmail(null);
        }

        clienteDto.setEndereco(pedido.getCliente().getEndereco());
        dto.setCliente(clienteDto);

        // Mapear itens para List<ItemPedidoResponseDTO>
        List<ItemPedidoResponseDTO> itensDto = pedido.getItens().stream()
            .map(this::toItemPedidoResponseDTO) // Swagger (conversão itens)
            .collect(Collectors.toList());
        dto.setItens(itensDto);

        dto.setStatus(pedido.getStatus().name());
        dto.setDataCriacao(pedido.getDataCriacao());
        dto.setCodigoDesconto(pedido.getCodigoDesconto());
        dto.setValorTotal(pedido.getItens().stream()
            .map(ItemPedido::getPrecoTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add));
        dto.setValorFinal(pedido.getValorFinal());

        return dto;
    }

    // Converte entidade ItemPedido para ItemPedidoResponseDTO // Swagger (método de conversão DTO)
    public ItemPedidoResponseDTO toItemPedidoResponseDTO(ItemPedido item) { // Swagger
        ItemPedidoResponseDTO dto = new ItemPedidoResponseDTO();
        dto.setId(item.getId());
        dto.setProdutoId(item.getProduto().getId());
        dto.setNomeProduto(item.getProduto().getNome());
        dto.setQuantidade(item.getQuantidade());
        dto.setPrecoUnitario(item.getPrecoUnitario());
        dto.setDesconto(item.getDesconto());
        dto.setPrecoTotal(item.getPrecoTotal());
        return dto;
    }
}

