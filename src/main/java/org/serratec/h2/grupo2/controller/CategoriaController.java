
package org.serratec.h2.grupo2.controller;

import java.util.List;

import org.serratec.h2.grupo2.DTO.CategoriaRequestDto;
import org.serratec.h2.grupo2.DTO.CategoriaResponseDto;
import org.serratec.h2.grupo2.DTO.CategoriaUpdateDto;
import org.serratec.h2.grupo2.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
// lucas: imports para Swagger/OpenAPI
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/categorias")
// lucas: define grupo e descrição para o controller na documentação Swagger
@Tag(name = "Categorias", description = "Endpoints para gerenciamento de categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    // lucas: documentação do endpoint listar todas categorias
    @Operation(summary = "Lista todas as categorias")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    public List<CategoriaResponseDto> listar() {
        return categoriaService.listarTodas();
    }

    // lucas: documentação do endpoint para criar categoria
    @Operation(summary = "Cria uma nova categoria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria criada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para criação")
    })
    @PostMapping
    public ResponseEntity<CategoriaResponseDto> criar(@RequestBody @Valid CategoriaRequestDto dto) {
        CategoriaResponseDto nova = categoriaService.criar(dto);
        return ResponseEntity.ok(nova);
    }

    // lucas: documentação do endpoint atualizar categoria pelo id
    @Operation(summary = "Atualiza uma categoria pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoriaResponseDto> atualizar(@PathVariable Long id,
            @RequestBody @Valid CategoriaUpdateDto dto) {
        CategoriaResponseDto atualizada = categoriaService.atualizar(id, dto);
        return ResponseEntity.ok(atualizada);
    }

    // lucas: documentação do endpoint deletar categoria pelo id
    @Operation(summary = "Deleta uma categoria pelo ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso"),
        @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        categoriaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
