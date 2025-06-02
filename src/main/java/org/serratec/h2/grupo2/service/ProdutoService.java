// Essa classe vai servir para implementar todas as funções da classe Produto, deixando funcional

package org.serratec.h2.grupo2.service;

import java.util.List;

import org.serratec.h2.grupo2.DTO.ProdutoRequestDTO;
import org.serratec.h2.grupo2.DTO.ProdutoResponseDTO;
import org.serratec.h2.grupo2.domain.Produto;
import org.serratec.h2.grupo2.mapper.ProdutoMapper;
import org.serratec.h2.grupo2.repository.FotoRepository;
import org.serratec.h2.grupo2.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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
	
	// POST: Inserir
    // Mapper converte o JSON para Produto depois converte para ProdutoResponse
    // Chamar apenas service.inserir(produto)
    public ProdutoResponseDTO inserir(@Valid ProdutoRequestDTO dto) {
    	Produto produto = produtoMapper.toProduto(dto);

//    	if (produto.getFoto() != null) {
//    	    produto.getFoto().setProduto(produto); // importante!
//    	}

    	Produto produtoSalvo = produtoRepository.save(produto); // salva ambos com cascade
    	return produtoMapper.toResponse(produtoSalvo);
    }
	
	// PUT: Atualizar
    // Mapper pega um JSON transforma em produto depois transforma em Produto Response
	// Chamar apenas service.atualizar(id, produto)
    public ProdutoResponseDTO atualizar(Long id, @Valid ProdutoRequestDTO dto) {
        if (!produtoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não cadastrado!");
        }

        Produto produto = produtoMapper.toProduto(dto);
        produto.setId(id); // garante que vai atualizar o correto
        Produto atualizado = produtoRepository.save(produto);
        return produtoMapper.toResponse(atualizado);
    }

	// Deletar um item
	// Chamar apenas service.remover(id)
    public void remover(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }
        produtoRepository.deleteById(id);
    }
    
    // Método para listar itens em promoção
    public List<ProdutoResponseDTO> listarPromocoes() {
        return produtoRepository.findAll().stream()
            .filter(p -> p.getPrecoPromocional() != null && p.getPrecoPromocional().compareTo(p.getPreco()) < 0)
            .map(produtoMapper::toResponse)
            .toList();
    }
}
