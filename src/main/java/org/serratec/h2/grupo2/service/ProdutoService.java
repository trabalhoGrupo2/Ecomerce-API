// Essa classe vai servir para implementar todas as funções da classe Produto, deixando funcional

package org.serratec.h2.grupo2.service;

import java.util.List;
import java.util.Optional;

import org.serratec.h2.grupo2.domain.Foto;
import org.serratec.h2.grupo2.domain.Produto;
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
	
	// GET: Ler a lista de produtos
	// Chamar apenas service.listar no controller
	public List<Produto> listar() {
        return produtoRepository.findAll();
    }
	
	// GET: ID
	// Chamar apenas service.pesquisar
	public Optional<Produto> pesquisar(Long id) {
        return produtoRepository.findById(id);
    }
	
	// POST: Inserir 
	// Chamar apenas service.inserir(produto)
	public Produto inserir(@Valid Produto produto) {
	    Produto produtoSalvo = produtoRepository.save(produto);
	    if (produto.getFoto() != null) {
	        Foto foto = produto.getFoto();
	        foto.setProduto(produtoSalvo);
	        fotoRepository.save(foto);
	    }

	    return produtoSalvo; // retorne ela no final
	}
	
	// PUT: Atualizar
	// Chamar apenas service.atualizar(id, produto)
	public Optional<Produto> atualizar(Long id, @Valid Produto produto) {
		
		// Caso o id seja diferente dos que temos, retorna vazia
        if (!produtoRepository.existsById(id)) {
            return Optional.empty();
        }
        // Salva o produto atualizado e retorna
        produto.setId(id);
        Produto atualizado = produtoRepository.save(produto);
        return Optional.of(atualizado);
    }
	
	// Deletar um item
	// Chamar apenas service.remover(id)
	public void remover(@PathVariable Long id) {
	    if (!produtoRepository.existsById(id)) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
	    }
	    produtoRepository.deleteById(id);
	}
}
