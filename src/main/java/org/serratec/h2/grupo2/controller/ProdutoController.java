package org.serratec.h2.grupo2.controller;

import java.util.List;

import org.serratec.h2.grupo2.DTO.ProdutoRequestDTO;
import org.serratec.h2.grupo2.DTO.ProdutoResponseDTO;
import org.serratec.h2.grupo2.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;
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
@RequestMapping("/produtos")
@Tag(name = "Produtos", description = "Endpoints para gerenciamento de produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoService service;

	// Swagger, Lista todos os produtos cadastrados
	@Operation(summary = "Listar todos os produtos", description = "Retorna uma lista com todos os produtos cadastrados")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponseDTO.class)))
	})
	@GetMapping
	public ResponseEntity<List<ProdutoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

	// Swagger, Busca um produto pelo seu ID
	@Operation(summary = "Buscar produto por ID", description = "Busca um produto pelo seu ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Produto encontrado",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponseDTO.class))),
		@ApiResponse(responseCode = "404", description = "Produto não encontrado")
	})
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoResponseDTO> pesquisar(@Parameter(description = "ID do produto", example = "1") @PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.pesquisar(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

	// Swagger, Lista produtos em promoção
	@Operation(summary = "Listar produtos em promoção", description = "Retorna a lista de produtos que estão em promoção")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Lista de promoções retornada com sucesso",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponseDTO.class)))
	})
	@GetMapping("/promocoes")
	public ResponseEntity<List<ProdutoResponseDTO>> listarPromocoes() {
	    List<ProdutoResponseDTO> promocoes = service.listarPromocoes();
	    return ResponseEntity.ok(promocoes);
	}

	// Swagger, Insere um novo produto no sistema
	@Operation(summary = "Inserir novo produto", description = "Insere um novo produto no sistema")
	@ApiResponses({
		@ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponseDTO.class))),
		@ApiResponse(responseCode = "400", description = "Dados inválidos para criação")
	})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponseDTO inserir(@Valid @RequestBody ProdutoRequestDTO dto) {
        return service.inserir(dto);
    }

	// Swagger, Atualiza os dados de um produto existente pelo ID
	@Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto pelo ID")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
			content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProdutoResponseDTO.class))),
		@ApiResponse(responseCode = "404", description = "Produto não encontrado")
	})
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(
		@Parameter(description = "ID do produto", example = "1") @PathVariable Long id,
		@Valid @RequestBody ProdutoRequestDTO dto) {
        try {
            ProdutoResponseDTO atualizado = service.atualizar(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

	// Swagger, Remove um produto pelo seu ID
	@Operation(summary = "Remover produto", description = "Remove um produto pelo seu ID")
	@ApiResponses({
		@ApiResponse(responseCode = "204", description = "Produto removido com sucesso"),
		@ApiResponse(responseCode = "404", description = "Produto não encontrado"),
		@ApiResponse(responseCode = "500", description = "Erro interno no servidor")
	})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@Parameter(description = "ID do produto", example = "1") @PathVariable Long id) {
	    try {
	        service.remover(id);
	        return ResponseEntity.noContent().build();
	    } catch (EntityNotFoundException e) {
	        return ResponseEntity.notFound().build();
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
		
}
