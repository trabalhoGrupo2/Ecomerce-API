package org.serratec.h2.grupo2.controller;

import java.util.List;
import java.util.Optional;

import org.serratec.h2.grupo2.domain.Produto;
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

import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	// Injetar service
	@Autowired
	private ProdutoService service;

	// GET: TODOS
	@GetMapping
	public ResponseEntity<List<Produto>> listar() {
		return ResponseEntity.ok(service.listar());
	}
	
	// GET: ID
	@GetMapping("/{id}")
    public ResponseEntity<Produto> pesquisar(@PathVariable Long id) {
        Optional<Produto> produtoOpt = service.pesquisar(id);
        if (produtoOpt.isPresent()) {
            return ResponseEntity.ok(produtoOpt.get());
        }
        return ResponseEntity.notFound().build();
    }

	// POST: INSERIR
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto inserir(@Valid @RequestBody Produto produto) {
        return service.inserir(produto);
    }


    // PUT: ATUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @Valid @RequestBody Produto produto) {
        Optional<Produto> produtoAtualizado = service.atualizar(id, produto);
        return produtoAtualizado.map(ResponseEntity::ok)
                                 .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: REMOVER
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        try {
            service.remover(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
		
}
