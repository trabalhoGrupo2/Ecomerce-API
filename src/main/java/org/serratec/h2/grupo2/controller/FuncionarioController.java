package org.serratec.h2.grupo2.controller;

import java.util.List;
import org.serratec.h2.grupo2.DTO.funcionario.FuncionarioCompletUpdateForFuncionarioDto;
import org.serratec.h2.grupo2.DTO.funcionario.FuncionarioCompletUpdateForGestorDto;
import org.serratec.h2.grupo2.DTO.funcionario.FuncionarioRequestDto;
import org.serratec.h2.grupo2.DTO.funcionario.FuncionarioResponseDto;
import org.serratec.h2.grupo2.DTO.funcionario.FuncionarioUpdateForGestorDto;
import org.serratec.h2.grupo2.enuns.Cargo;
import org.serratec.h2.grupo2.enuns.NivelAcesso;
import org.serratec.h2.grupo2.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/funcionario")
@Tag(name = "Funcionários", description = "Endpoints para gerenciamento de funcionários")
public class FuncionarioController {
	
	@Autowired
	private FuncionarioService service;

	// Swagger, Cadastra um novo funcionário no sistema
	@Operation(summary = "Cadastro de funcionário", description = "Cadastra um novo funcionário no sistema")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Funcionário cadastrado com sucesso",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioResponseDto.class))),
		@ApiResponse(responseCode = "400", description = "Dados inválidos para cadastro")
	})
	@PostMapping("/cadastro")
	public FuncionarioResponseDto cadastrarFuncionario(@Valid @RequestBody FuncionarioRequestDto request) {
		return service.cadastrarFuncionario(request);
	}
	
	// Swagger, Atualiza completamente os dados do cadastro do funcionário
	@Operation(summary = "Atualização completa de cadastro", description = "Atualiza os dados completos do cadastro do funcionário")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Cadastro atualizado com sucesso",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioResponseDto.class))),
		@ApiResponse(responseCode = "400", description = "Dados inválidos para atualização")
	})
	@PutMapping("/atualizarCadastro")
	public FuncionarioResponseDto atualizacaoCadastro(@Valid @RequestBody FuncionarioCompletUpdateForFuncionarioDto request) {
		return service.atualizacaoCadastro(request);
	}
	
	// Swagger, Atualiza os dados completos do funcionário via gestor
	@Operation(summary = "Atualização completa para gestor", description = "Atualiza os dados completos do funcionário via gestor")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Funcionário atualizado com sucesso",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioResponseDto.class))),
		@ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
	})
	@PutMapping("/atualizarFuncionario")
	public FuncionarioResponseDto atualizacaoCompleta(
		@Parameter(description = "ID do funcionário a ser atualizado", example = "1") @PathVariable Long id,
		@Valid @RequestBody FuncionarioUpdateForGestorDto request) {
		return service.atualizacaoCompleta(id, request);
	}
	
	// Swagger, Atualiza parcialmente os dados do funcionário
	@Operation(summary = "Atualização parcial", description = "Atualiza parcialmente os dados do funcionário")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioResponseDto.class))),
		@ApiResponse(responseCode = "400", description = "Dados inválidos para atualização")
	})
	@PatchMapping("/atualizacaoParcial")
	public FuncionarioResponseDto atualizacaoParcial(@RequestBody FuncionarioCompletUpdateForFuncionarioDto request) {
		return service.atualizacaoParcial(request);
	}
	
	// Swagger, Atualiza parcialmente dados do funcionário via gestor
	@Operation(summary = "Atualização parcial para gestor", description = "Atualiza parcialmente dados do funcionário via gestor")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Dados atualizados com sucesso",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioResponseDto.class))),
		@ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
	})
	@PatchMapping("/atualizacaoParcialGestor/{id}")
	public FuncionarioResponseDto atualizacaoParcialGestor(
		@Parameter(description = "ID do funcionário", example = "1") @PathVariable Long id,
		@RequestBody FuncionarioCompletUpdateForGestorDto request) {
		return service.atualizacaoParcialGestor(id, request);
	}
	
	// Swagger, Desativa a conta do funcionário pelo ID
	@Operation(summary = "Desativar conta", description = "Desativa a conta do funcionário pelo ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Conta desativada com sucesso",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioResponseDto.class))),
		@ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
	})
	@PatchMapping("/desativarConta/{id}")
	public FuncionarioResponseDto desativarConta(@Parameter(description = "ID do funcionário", example = "1") @PathVariable Long id) {
		return service.desativarConta(id);
	}
	
	// Swagger, Ativa a conta do funcionário pelo ID
	@Operation(summary = "Ativar conta", description = "Ativa a conta do funcionário pelo ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Conta ativada com sucesso",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioResponseDto.class))),
		@ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
	})
	@PatchMapping("/ativarConta/{id}")
	public FuncionarioResponseDto ativarConta(@Parameter(description = "ID do funcionário", example = "1") @PathVariable Long id) {
		return service.ativarConta(id);
	}
	
	// Swagger, Retorna a lista completa de funcionários cadastrados
	@Operation(summary = "Listar todos os funcionários", description = "Retorna a lista completa de funcionários cadastrados")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioResponseDto.class)))
	})
	@GetMapping("/listarFuncionarios")
	public List<FuncionarioResponseDto> listar() {
		return service.listar();
	}
	
	// Swagger, Busca um funcionário pelo seu ID
	@Operation(summary = "Buscar funcionário por ID", description = "Busca um funcionário pelo seu ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Funcionário encontrado",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioResponseDto.class))),
		@ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
	})
	@GetMapping("/buscarPorId/{id}")
	public FuncionarioResponseDto buscarPorId(@Parameter(description = "ID do funcionário", example = "1") @PathVariable Long id) {
		return service.buscarPorId(id);
	}
	
	// Swagger, Busca funcionários pelo nome informado
	@Operation(summary = "Buscar funcionários por nome", description = "Busca funcionários pelo nome informado")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Funcionários encontrados",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioResponseDto.class)))
	})
	@GetMapping("/listarPorNome/{nome}")
	public List<FuncionarioResponseDto> listarPorNome(@Parameter(description = "Nome para busca", example = "João") @PathVariable String nome) {
		return service.listarPorNome(nome);
	}
	
	// Swagger, Retorna funcionários filtrados pelo cargo
	@Operation(summary = "Listar funcionários pelo cargo", description = "Retorna funcionários filtrados pelo cargo")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Funcionários retornados com sucesso",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioResponseDto.class)))
	})
	@GetMapping("/listarPeloCargo/{cargo}")
	public List<FuncionarioResponseDto> listarPeloCargo(@Parameter(description = "Cargo para filtro") @PathVariable Cargo cargo) {
		return service.listarPeloCargo(cargo);
	}
	
	// Swagger, Retorna funcionários filtrados pelo nível de acesso
	@Operation(summary = "Listar funcionários por nível de acesso", description = "Retorna funcionários filtrados pelo nível de acesso")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Funcionários retornados com sucesso",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioResponseDto.class)))
	})
	@GetMapping("/listarPeloNivelAcesso/{acesso}")
	public List<FuncionarioResponseDto> listarPorNivelDeAcesso(@Parameter(description = "Nível de acesso para filtro") @PathVariable NivelAcesso acesso) {
		return service.listarPorNivelDeAcesso(acesso);
	}
	
	// Swagger, Retorna a lista de funcionários com contas ativas
	@Operation(summary = "Listar contas ativas", description = "Retorna a lista de funcionários com contas ativas")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioResponseDto.class)))
	})
	@GetMapping("/listarContasAtivas")
	public List<FuncionarioResponseDto> listarContasAtivas() {
		return service.listarContaAtivaOuDesativada(true);
	}
	
	// Swagger, Retorna a lista de funcionários com contas desativadas
	@Operation(summary = "Listar contas desativadas", description = "Retorna a lista de funcionários com contas desativadas")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = FuncionarioResponseDto.class)))
	})
	@GetMapping("/listarContasDesativadas")
	public List<FuncionarioResponseDto> listarContasDesativadas() {
		return service.listarContaAtivaOuDesativada(false);
	}
	
	// Swagger, Exclui um funcionário do sistema pelo seu ID
	@Operation(summary = "Deletar funcionário", description = "Exclui um funcionário do sistema pelo seu ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Funcionário deletado com sucesso"),
		@ApiResponse(responseCode = "404", description = "Funcionário não encontrado")
	})
	@DeleteMapping("/deletarFuncionario/{id}")
	public void deletarFuncionario(@Parameter(description = "ID do funcionário", example = "1") @PathVariable Long id) {
		service.excluirFuncionario(id);
	}
}
