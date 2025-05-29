package org.serratec.h2.grupo2.controller;

import org.serratec.h2.grupo2.DTO.CategoriaDTO;
import org.serratec.h2.grupo2.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
	
	@Autowired
	private CategoriaService categoriaService;

	@GetMapping
	public List<CategoriaDTO> listarCategorias() {
		return categoriaService.listarTodas();
	}

	@PostMapping
	public CategoriaDTO criarCategoria(@RequestBody CategoriaDTO categoriaDTO) {
		return categoriaService.criarCategoria(categoriaDTO);
	}

	@PutMapping("/{id}")
	public ResponseEntity<CategoriaDTO> editarCategoria(@PathVariable Long id, @RequestBody CategoriaDTO categoriaDTO) {
		CategoriaDTO categoriaAtualizada = categoriaService.editarCategoria(id, categoriaDTO);
		return ResponseEntity.ok(categoriaAtualizada);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletarCategoria(@PathVariable Long id) {
		categoriaService.deletarCategoria(id);
		return ResponseEntity.noContent().build();
	}
}