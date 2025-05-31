// ATUALIZADO DANDARA
package org.serratec.h2.grupo2.controller;

import java.io.IOException;
import java.util.List;

import org.serratec.h2.grupo2.DTO.ProdutoRequestDTO;
import org.serratec.h2.grupo2.DTO.ProdutoResponseDTO;
import org.serratec.h2.grupo2.domain.Foto;
import org.serratec.h2.grupo2.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	// Injetar service
	@Autowired
	private ProdutoService service;

	// GET: TODOS
	// ResponseEntity permite customizar o status HTTP
	@GetMapping
	public ResponseEntity<List<ProdutoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

	
	// GET: ID
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoResponseDTO> pesquisar(@PathVariable Long id) {
		// Chama o método pesquisar e caso exista o ID vai retornar o status HTTP
		// ok com ProdutoResponse no corpo da resposta
        try {
            return ResponseEntity.ok(service.pesquisar(id));
        // Retorna error 404
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

	// POST: INSERIR
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public ProdutoResponseDTO inserir(@Valid @RequestBody ProdutoRequestDTO dto) {
//        return service.inserir(dto);
//    }
    
    
    
//    @PostMapping(value = "/com-foto", consumes = "multipart/form-data")
    @PostMapping(consumes = "multipart/form-data")
	public ResponseEntity<ProdutoResponseDTO> inserir(
	    @RequestPart("produto") ProdutoRequestDTO produtoJson,
	    @RequestPart("foto") MultipartFile fotoFile) throws IOException {

	    // Criar entidade Foto
	    Foto foto = new Foto();
	    foto.setDados(fotoFile.getBytes());
	    foto.setNome(fotoFile.getOriginalFilename());
	    foto.setTipo(fotoFile.getContentType());

	    produtoJson.setFoto(foto);

	    ProdutoResponseDTO response = service.inserir(produtoJson);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
    
    
    
    


    // PUT: ATUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<ProdutoResponseDTO> atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequestDTO dto) {
        try {
            ProdutoResponseDTO atualizado = service.atualizar(id, dto);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE: REMOVER
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
	    try {
	        service.remover(id);
	        return ResponseEntity.noContent().build(); // Retorna 204 No Content ao remover com sucesso
	    } catch (EntityNotFoundException e) {
	        return ResponseEntity.notFound().build(); // Retorna 404 caso não encontre o recurso
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 para erros inesperados
	    }
	}
		
}