package org.serratec.h2.grupo2.service;

import org.serratec.h2.grupo2.DTO.CategoriaDTO;
import org.serratec.h2.grupo2.domain.Categoria;
import org.serratec.h2.grupo2.execption.CategoriaException;
import org.serratec.h2.grupo2.mapper.CategoriaMapper;
import org.serratec.h2.grupo2.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private CategoriaMapper categoriaMapper;

	public List<CategoriaDTO> listarTodas() {
		return categoriaRepository.findAll().stream().map(categoriaMapper::toDTO).collect(Collectors.toList());
	}

	public CategoriaDTO criarCategoria(CategoriaDTO categoriaDTO) {
		if (categoriaRepository.findByNome(categoriaDTO.getNome()).isPresent()) {
			throw new CategoriaException("Já existe uma categoria com o nome: " + categoriaDTO.getNome());
		}
		Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		return categoriaMapper.toDTO(categoriaSalva);
	}

	public CategoriaDTO editarCategoria(Long id, CategoriaDTO categoriaDTO) {
		Categoria categoria = categoriaMapper.toEntity(categoriaDTO);
		categoria.setId(id);
		Categoria categoriaAtualizada = categoriaRepository.save(categoria);
		return categoriaMapper.toDTO(categoriaAtualizada);
	}

	public void deletarCategoria(Long id) {
		categoriaRepository.deleteById(id);
	}
}