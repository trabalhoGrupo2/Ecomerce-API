// ATUALIZADO DANDARA
// Essa classe vai servir para implementar todas as funções da classe Produto, deixando funcional

package org.serratec.h2.grupo2.service;

import java.util.List;

import org.serratec.h2.grupo2.DTO.ProdutoRequestDTO;
import org.serratec.h2.grupo2.DTO.ProdutoResponseDTO;
import org.serratec.h2.grupo2.domain.Foto;
import org.serratec.h2.grupo2.domain.Produto;
import org.serratec.h2.grupo2.mapper.ProdutoMapper;
import org.serratec.h2.grupo2.repository.FotoRepository;
import org.serratec.h2.grupo2.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;

// Representa uma camada de serviço
@Service
public class ProdutoService {
	
	// Injetar a interface para procurar no banco de dados
	@Autowired
	private ProdutoRepository produtoRepository;
	
	// Injetar a interface para procurar no banco de dados
	@Autowired
	private FotoRepository fotoRepository;
	
	// Injetar a interface para procurar no banco de dados
	@Autowired
	private ProdutoMapper produtoMapper;
	
	// GET: Ler a lista de produtos
	// Mapper faz a conversão de Produto para ProdutoResponse
	// Chamar apenas service.listar no controller
	public List<ProdutoResponseDTO> listar() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtoMapper.toListResponse(produtos);
    }
	
	// GET: ID
	// Chamar apenas service.pesquisar
	// Mapper faz a conversão do Produto para ProdutoResponse
	// GET: Buscar por ID
    public ProdutoResponseDTO pesquisar(Long id) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));
        return produtoMapper.toResponse(produto);
    }
	

	// Deletar um item
	// Chamar apenas service.remover(id)
    public void remover(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }
}
