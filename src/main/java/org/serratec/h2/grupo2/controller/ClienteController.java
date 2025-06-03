package org.serratec.h2.grupo2.controller;

import java.util.List;

import org.serratec.h2.grupo2.DTO.cliente.ClienteRequestDto;
import org.serratec.h2.grupo2.DTO.cliente.ClienteResponseDto;
import org.serratec.h2.grupo2.DTO.cliente.ClienteUpdateDto;
import org.serratec.h2.grupo2.DTO.cliente.quantidadeClientes.QuantidadeEstadoDto;
import org.serratec.h2.grupo2.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

// Swagger/OpenAPI imports
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/cliente")
@Tag(name = "Clientes", description = "Endpoints para gerenciamento de clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    // Swagger, Cadastra um novo cliente no sistema
    @Operation(summary = "Cadastro de cliente", description = "Cadastra um novo cliente no sistema")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente cadastrado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para cadastro")
    })
    @PostMapping("/cadastro")
    public ClienteResponseDto cadastrar(@Valid @RequestBody ClienteRequestDto request) {
        return service.cadastrarCliente(request);
    }

    // Swagger, Atualiza os dados cadastrais do cliente
    @Operation(summary = "Atualização cadastro", description = "Atualiza os dados cadastrais do cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cadastro atualizado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização")
    })
    @PutMapping("/atualizarCadastro")
    public ClienteResponseDto atualizacaoCadastro(@Valid @RequestBody ClienteRequestDto request) {
        return service.atualizacaoCadastro(request);
    }

    // Swagger, Atualiza completamente os dados de um cliente pelo seu ID
    @Operation(summary = "Atualização completa por ID", description = "Atualiza completamente os dados de um cliente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PutMapping("/atualizarCliente/{id}")
    public ClienteResponseDto atualizacaoCompleta(
        @Parameter(description = "ID do cliente a ser atualizado", example = "1") @PathVariable Long id,
        @Valid @RequestBody ClienteRequestDto request) {
        return service.atualizacaoCadastroClientePorFuncionario(id, request);
    }

    // Swagger, Atualiza parcialmente os dados do cliente
    @Operation(summary = "Atualização parcial de dados", description = "Atualiza parcialmente os dados do cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização")
    })
    @PatchMapping("/atualizacaoParcial")
    public ClienteResponseDto atualizacaoParcial(@RequestBody ClienteUpdateDto request) {
        return service.atualizacaoParcial(request);
    }

    // Swagger, Desativa a conta do cliente pelo ID
    @Operation(summary = "Desativar conta", description = "Desativa a conta do cliente pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Conta desativada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PatchMapping("/desativarConta/{id}")
    public ClienteResponseDto desativarConta(@Parameter(description = "ID do cliente para desativação", example = "1") @PathVariable Long id) {
        return service.desativarConta(id);
    }

    // Swagger, Ativa a conta do cliente pelo ID
    @Operation(summary = "Ativar conta", description = "Ativa a conta do cliente pelo ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Conta ativada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PatchMapping("/ativarConta/{id}")
    public ClienteResponseDto ativarConta(@Parameter(description = "ID do cliente para ativação", example = "1") @PathVariable Long id) {
        return service.tivarConta(id);
    }

    // Swagger, Ativa a conta do cliente através do email e token
    @Operation(summary = "Ativação via email e token", description = "Ativa a conta do cliente através do email e token")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Conta ativada com sucesso",
            content = @Content(mediaType = "text/plain")),
        @ApiResponse(responseCode = "400", description = "Email ou token inválidos")
    })
    @PatchMapping("/ativarContaEmail")
    public ResponseEntity<String> ativarConta(
        @Parameter(description = "Email do cliente", example = "cliente@exemplo.com") @RequestParam String email,
        @Parameter(description = "Token de ativação", example = "token123") @RequestParam String token) {
        return service.ativarConta(email, token);
    }

    // Swagger, Retorna a lista completa de clientes cadastrados
    @Operation(summary = "Listar todos os clientes", description = "Retorna a lista completa de clientes cadastrados")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class)))
    })
    @GetMapping("/listarClientes")
    public List<ClienteResponseDto> listarTodos() {
        return service.listarTodos();
    }

    // Swagger, Busca um cliente pelo seu ID
    @Operation(summary = "Buscar cliente por ID", description = "Busca um cliente pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class))),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/buscarPorId/{id}")
    public ClienteResponseDto buscarPorId(@Parameter(description = "ID do cliente a ser buscado", example = "1") @PathVariable Long id) {
        return service.buscarPorId(id);
    }

    // Swagger, Busca clientes pelo nome informado
    @Operation(summary = "Buscar cliente por nome", description = "Busca clientes pelo nome informado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Clientes encontrados",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class)))
    })
    @GetMapping("/buscarPorNome/{nome}")
    public List<ClienteResponseDto> buscarPorNome(@Parameter(description = "Nome para busca", example = "João") @PathVariable String nome) {
        return service.buscarNome(nome);
    }

    // Swagger, Busca clientes pela cidade informada
    @Operation(summary = "Buscar cliente por cidade", description = "Busca clientes pela cidade informada")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Clientes encontrados",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class)))
    })
    @GetMapping("/buscarPorCidade/{cidade}")
    public List<ClienteResponseDto> buscarPorCidade(@Parameter(description = "Cidade para busca", example = "Rio de Janeiro") @PathVariable String cidade) {
        return service.pesquisaPorCidade(cidade);
    }

    // Swagger, Busca clientes pelo estado informado
    @Operation(summary = "Buscar cliente por estado", description = "Busca clientes pelo estado informado")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Clientes encontrados",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ClienteResponseDto.class)))
    })
    @GetMapping("/buscarPorEstado/{estado}")
    public List<ClienteResponseDto> buscarPorEstado(@Parameter(description = "Estado para busca", example = "RJ") @PathVariable String estado) {
        return service.pesquisaPorEstado(estado);
    }

    // Swagger, Retorna a quantidade de clientes agrupados por estado e cidade
    @Operation(summary = "Quantidade de clientes por estado e cidade", description = "Retorna a quantidade de clientes agrupados por estado e cidade")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Quantidade retornada com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = QuantidadeEstadoDto.class)))
    })
    @GetMapping("/quantidadePorEstadoECidade")
    public List<QuantidadeEstadoDto> quantidadeClientesPorEstadoECidade() {
        return service.listarQuantidadeClientes();
    }

    // Swagger, Remove um cliente do sistema pelo seu ID
    @Operation(summary = "Excluir cliente permanentemente", description = "Remove um cliente do sistema pelo seu ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente excluído com sucesso",
            content = @Content(mediaType = "text/plain")),
        @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @DeleteMapping("/excluirCliente/{id}")
    public ResponseEntity<String> excluirCliente(@Parameter(description = "ID do cliente a ser excluído", example = "1") @PathVariable Long id) {
        return service.excluirCliente(id);
    }
}
