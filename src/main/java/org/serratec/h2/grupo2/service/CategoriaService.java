package org.serratec.h2.grupo2.service;


import java.util.List;
import java.util.stream.Collectors;
import org.serratec.h2.grupo2.DTO.CategoriaRequestDto;
import org.serratec.h2.grupo2.DTO.CategoriaResponseDto;
import org.serratec.h2.grupo2.DTO.CategoriaUpdateDto;
import org.serratec.h2.grupo2.domain.Categoria;
import org.serratec.h2.grupo2.mapper.CategoriaMapper;
import org.serratec.h2.grupo2.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private CategoriaMapper categoriaMapper;
    
 // Retorna uma lista de todas as categorias no formato de DTO de resposta
    public List<CategoriaResponseDto> listarTodas() {
        return categoriaRepository.findAll() // Busca todas as categorias no banco
                .stream() // Cria um fluxo de dados
                .map(categoriaMapper::toResponseDto) // Converte cada entidade em um DTO de resposta
                .collect(Collectors.toList()); // Coleta os resultados em uma lista
    }

 // Cria uma nova categoria a partir de um DTO de requisição
    public CategoriaResponseDto criar(CategoriaRequestDto dto) {
    	 // Verifica se já existe uma categoria com o mesmo nome (ignorando maiúsculas/minúsculas)
        if (categoriaRepository.existsByNomeIgnoreCase(dto.getNome())) { 
            throw new RuntimeException("Categoria já existente com este nome.");
        }

        Categoria categoria = categoriaMapper.toEntity(dto); // Converte o DTO para entidade
        Categoria salva = categoriaRepository.save(categoria); // Salva a nova categoria no banco
        return categoriaMapper.toResponseDto(salva); // Retorna a categoria salva no formato DTO de resposta
    }

 // Atualiza uma categoria existente com base no ID e nos dados do DTO de atualização
    public CategoriaResponseDto atualizar(Long id, CategoriaUpdateDto dto) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada."));

        Categoria atualizada = categoriaMapper.toEntity(dto, categoria);
        Categoria salva = categoriaRepository.save(atualizada);
        return categoriaMapper.toResponseDto(salva);
    }

 // Remove uma categoria com base no ID
    public void deletar(Long id) {
        if (!categoriaRepository.existsById(id)) {
            throw new RuntimeException("Categoria não encontrada.");
        }
        categoriaRepository.deleteById(id);
    }
}