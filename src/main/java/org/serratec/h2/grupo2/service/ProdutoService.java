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

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private FotoRepository fotoRepository;

    @Autowired
    private ProdutoMapper produtoMapper;

    // GET: listar todos os produtos
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

    // DELETE: remover produto
    public void remover(Long id) {
        if (!produtoRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado");
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

    // POST: inserir novo produto
    public ProdutoResponseDTO inserir(ProdutoRequestDTO dto) {
        Produto produto = produtoMapper.toProduto(dto);

        // vincular categoria
        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
        produto.setCategoria(categoria);

        // salvar foto (se houver)
        Foto foto = dto.getFoto();
        if (foto != null) {
            fotoRepository.save(foto);
            produto.setFoto(foto);
        }

        produto.setDataCadastro(LocalDate.now());
        produto = produtoRepository.save(produto);
        return produtoMapper.toResponse(produto);
    }

    // PUT: atualizar produto existente
    public ProdutoResponseDTO atualizar(Long id, ProdutoRequestDTO dto) {
        Produto existente = produtoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

        Produto produto = produtoMapper.toProduto(dto);
        produto.setId(id);
        produto.setDataCadastro(existente.getDataCadastro());
        produto.setDataAtualizacao(LocalDate.now());

        // atualizar categoria
        Categoria categoria = categoriaRepository.findById(dto.getIdCategoria())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria não encontrada"));
        produto.setCategoria(categoria);

        // atualizar foto (opcional)
        Foto foto = dto.getFoto();
        if (foto != null) {
            fotoRepository.save(foto);
            produto.setFoto(foto);
        } else {
            produto.setFoto(existente.getFoto());
        }

        produto = produtoRepository.save(produto);
        return produtoMapper.toResponse(produto);
    }
}
