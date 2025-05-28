package org.serratec.h2.grupo2.produto;

import java.util.Optional;
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
	
	@Autowired
	private ProdutoRepository produtoRepository;

	// Pesquisar por item pelo Id
	
	
	/*@GetMapping
	public ResponseEntity<List<Produto>> listar() {
		return ResponseEntity.ok(produtoService.listar());
	}*/
	
		@GetMapping("/{id}")
		public ResponseEntity<Produto> pesquisar(@PathVariable Long id) {
			Optional<Produto> produtoOpt = produtoRepository.findById(id);
			if (produtoOpt.isPresent()) {
				return ResponseEntity.ok(produtoOpt.get());
			}
			return ResponseEntity.notFound().build();
		}

		//Inserir um item
		@PostMapping
		@ResponseStatus(HttpStatus.CREATED)
		public Produto inserir(@Valid @RequestBody Produto produto) {
			produto = produtoRepository.save(produto);
			return produto;
		}


		// Atualizando o item 
		@PutMapping("/{id}")
		public ResponseEntity<Produto> atualizar
		(@PathVariable Long id, @Valid @RequestBody Produto produto) {
			boolean exists = produtoRepository.existsById(id);
			if(!exists) {
				return ResponseEntity.notFound().build();
			}
			// produto.setId(id);
			produto = produtoRepository.save(produto);
			return ResponseEntity.ok(produto);
		}

		//Removendo um item 
		@DeleteMapping("/{id}")
		public ResponseEntity<Void> remover(@PathVariable Long id) {
			if (!produtoRepository.existsById(id)) {
				return ResponseEntity.notFound().build();
			}
			produtoRepository.deleteById(id);
			return ResponseEntity.noContent().build();
		}
		
	}
