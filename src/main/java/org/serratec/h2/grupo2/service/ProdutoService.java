package org.serratec.h2.grupo2.service;

import java.time.LocalDate;
import java.util.List;

import org.serratec.h2.grupo2.DTO.ProdutoRequestDTO;
import org.serratec.h2.grupo2.DTO.ProdutoResponseDTO;
import org.serratec.h2.grupo2.domain.Categoria;
import org.serratec.h2.grupo2.domain.Foto;
import org.serratec.h2.grupo2.domain.Produto;
import org.serratec.h2.grupo2.mapper.ProdutoMapper;
import org.serratec.h2.grupo2.repository.CategoriaRepository;
import org.serratec.h2.grupo2.repository.FotoRepository;
import org.serratec.h2.grupo2.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
	
	// Injetar a interface para procurar no banco de dados
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	
	// GET: Ler a lista de produtos
	// Mapper faz a conversão de Produto para ProdutoResponse
	// Chamar apenas service.listar no controller
	public List<ProdutoResponseDTO> listar() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtoMapper.toListResponse(produtos);
    }

    // GET: buscar produto por ID
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

    	if (produto.getFoto() != null) {
    	    produto.getFoto().setProduto(produto);
    	}

    	Produto produtoSalvo = produtoRepository.save(produto); // salva ambos com cascade
    	return produtoMapper.toResponse(produtoSalvo);
    }
	
	// PUT: Atualizar
    // Mapper pega um JSON transforma em produto depois transforma em Produto Response
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
    	// Produto existente recebe o Produto do ID passado
        Produto produtoExistente = produtoRepository.findById(id)
        // Caso não exista, "Produto não encontrado"
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

        produtoExistente.setNome(dto.getNome());
        produtoExistente.setDescricao(dto.getDescricao());
        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
        produtoExistente.setCategoria(categoria);
        produtoExistente.setPreco(dto.getPreco());
        produtoExistente.setPrecoPromocional(dto.getPrecoPromocional());
        produtoExistente.setEstoque(dto.getEstoque());
        produtoExistente.setFabricante(dto.getFabricante());
        produtoExistente.setAtivo(dto.getAtivo());
        produtoExistente.setDataAtualizacao(LocalDate.now());

        // Se no request, a foto for diferente de nula e os dados também
        // Cria uma entidade foto, igualando a foto do produto existente
        if (dto.getFoto() != null && dto.getFoto().getDados() != null) {
            Foto foto = produtoExistente.getFoto();
            
            // Se a foto for nula, tudo será mantido
            if (foto == null) {
                foto = new Foto();
                foto.setProduto(produtoExistente);
            }
            
            // Se for diferente, atualizará a foto com o request
            foto.setDados(dto.getFoto().getDados());
            foto.setNome(dto.getFoto().getNome());
            foto.setTipo(dto.getFoto().getTipo());

            produtoExistente.setFoto(foto);
        }
        produtoRepository.deleteById(id);
    }

    // GET: listar produtos com preço promocional
    public List<ProdutoResponseDTO> listarPromocoes() {
        return produtoRepository.findAll().stream()
                .filter(p -> p.getPrecoPromocional() != null && p.getPrecoPromocional().compareTo(p.getPreco()) < 0)
                .map(produtoMapper::toResponse)
                .toList();
    }

        // Produto atualizado é salvo e transformado em response para retorno ao usuário
        Produto atualizado = produtoRepository.save(produtoExistente);
        return produtoMapper.toResponse(atualizado);
    }
    
	// Deletar um item
	// Chamar apenas service.remover(id)
    public void remover(Long id) {
    	// Se existir o produto com aquele ID, ele será deletado..
    	// Caso contrário aparecerá "Produto não encontrado"
        if (!produtoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
        }

        produto = produtoRepository.save(produto);
        return produtoMapper.toResponse(produto);
    }
    
    // Método para listar itens em promoção
    public List<ProdutoResponseDTO> listarPromocoes() {
    	// Busca todos os produtos do banco e transforma essa lista em um Stream Java, que permite filtro
        return produtoRepository.findAll().stream()
            .filter(p -> p.getPrecoPromocional() != null && p.getPrecoPromocional().compareTo(p.getPreco()) < 0)
            // Transforma em produtoResponse e embaixo transforma em uma lista
            .map(produtoMapper::toResponse)
            .toList();
    }
}
