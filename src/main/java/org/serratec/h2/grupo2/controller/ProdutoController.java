// ATUALIZADO DANDARA

package org.serratec.h2.grupo2.controller;
import java.util.List;

import org.serratec.h2.grupo2.DTO.ProdutoRequestDTO;
import org.serratec.h2.grupo2.DTO.ProdutoResponseDTO;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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


		try {
			return ResponseEntity.ok(service.pesquisar(id)); 
			// Retorna error 404
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	// POST: INSERIR
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoResponseDTO inserir(@Valid @RequestBody ProdutoRequestDTO dto) {
		return service.inserir(dto);
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

        try {
            return ResponseEntity.ok(service.pesquisar(id));
        // Retorna error 404
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

	// POST: INSERIR
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProdutoResponseDTO inserir(@Valid @RequestBody ProdutoRequestDTO dto) {
        return service.inserir(dto);
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