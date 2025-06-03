package org.serratec.h2.grupo2.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.serratec.h2.grupo2.DTO.FreteDTO;
import org.serratec.h2.grupo2.DTO.PedidoRequestDTO;
import org.serratec.h2.grupo2.DTO.PedidoResponseDTO;
import org.serratec.h2.grupo2.domain.Pedido;
import org.serratec.h2.grupo2.mapper.PedidoMapper;
import org.serratec.h2.grupo2.repository.PedidoRepository;
import org.serratec.h2.grupo2.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;

// Swagger/OpenAPI imports
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/pedidos")
@Tag(name = "Pedidos", description = "Endpoints para gerenciamento de pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoRepository repository;

    @Autowired
    private PedidoMapper pedidoMapper;

    // Criar novo pedido
    @Operation(summary = "Criar novo pedido", description = "Insere um novo pedido com os dados fornecidos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para criação")
    })
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@RequestBody PedidoRequestDTO pedidoDTO) {
        Pedido pedido = pedidoService.criarPedido(pedidoDTO);
        PedidoResponseDTO dto = pedidoMapper.toResponse(pedido);
        return ResponseEntity.ok(dto);
    }

    // Editar pedido pelo ID
    @Operation(summary = "Editar pedido", description = "Edita um pedido existente pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido atualizado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> editarPedido(
            @Parameter(description = "ID do pedido a ser editado", example = "1") @PathVariable Long id,
            @RequestBody PedidoRequestDTO pedidoDTO) {
        Pedido pedido = pedidoService.editarPedido(id, pedidoDTO);
        PedidoResponseDTO dto = pedidoMapper.toResponse(pedido);
        return ResponseEntity.ok(dto);
    }

    // Alterar status do pedido
    @Operation(summary = "Alterar status do pedido", description = "Altera o status do pedido pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Status do pedido alterado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<PedidoResponseDTO> alterarStatus(
            @Parameter(description = "ID do pedido", example = "1") @PathVariable Long id,
            @Parameter(description = "Novo status do pedido", example = "ENVIADO") @RequestParam String status) {
        Pedido pedido = pedidoService.alterarStatus(id, status);
        PedidoResponseDTO dto = pedidoMapper.toResponse(pedido);
        return ResponseEntity.ok(dto);
    }

    // Buscar pedido por ID
    @Operation(summary = "Buscar pedido por ID", description = "Busca um pedido pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> buscarPorId(
            @Parameter(description = "ID do pedido", example = "1") @PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        PedidoResponseDTO dto = pedidoMapper.toResponse(pedido);
        return ResponseEntity.ok(dto);
    }

    // Listar todos os pedidos
    @Operation(summary = "Listar todos os pedidos", description = "Retorna uma lista com todos os pedidos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDTO.class)))
    })
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarTodos() {
        List<Pedido> pedidos = pedidoService.listarTodos();
        List<PedidoResponseDTO> dtos = pedidos.stream()
            .map(pedidoMapper::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Remover pedido pelo ID
    @Operation(summary = "Remover pedido", description = "Remove um pedido pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pedido removido com sucesso"),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@Parameter(description = "ID do pedido a ser removido", example = "1") @PathVariable Long id) {
        Pedido pedido = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pedido com ID " + id + " não encontrado."));
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Listar pedidos por cliente
    @Operation(summary = "Listar pedidos por cliente", description = "Retorna pedidos filtrados pelo ID do cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedidos retornados com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDTO.class)))
    })
    @GetMapping("/por-cliente")
    public ResponseEntity<List<PedidoResponseDTO>> listarPorCliente(
            @Parameter(description = "ID do cliente", example = "1") @RequestParam Long clienteId) {
        List<Pedido> pedidos = pedidoService.listarPorClienteId(clienteId);
        List<PedidoResponseDTO> dtos = pedidos.stream()
            .map(pedidoMapper::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    // Calcular frete do pedido
    @Operation(summary = "Calcular frete do pedido", description = "Calcula o frete do pedido pelo ID e distância em km")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Frete calculado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PatchMapping("/{id}/frete")
    public ResponseEntity<PedidoResponseDTO> calcularFrete(
            @Parameter(description = "ID do pedido", example = "1") @PathVariable Long id,
            @RequestBody FreteDTO freteDTO) {
        Pedido pedido = pedidoService.calcularFrete(id, freteDTO.getDistanciaKm());
        PedidoResponseDTO dto = pedidoMapper.toResponse(pedido);
        return ResponseEntity.ok(dto);
    }
}


