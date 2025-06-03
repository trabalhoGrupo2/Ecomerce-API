package org.serratec.h2.grupo2.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.serratec.h2.grupo2.domain.Categoria;
import org.serratec.h2.grupo2.domain.Foto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ProdutoResponseDTO {
    
    // Swagger, ID do produto
    @Schema(description = "Identificador único do produto", example = "1")
    private Long id;
    
    // Swagger, Nome do produto
    @Schema(description = "Nome do produto", example = "Smartphone X")
    private String nome;
    
    // Swagger, Descrição do produto
    @Schema(description = "Descrição do produto", example = "Smartphone com tela de 6.5 polegadas e câmera de 12 MP")
    private String descricao;
    
    // Swagger, ID da categoria do produto
    @Schema(description = "ID da categoria do produto", example = "1")
    private Long idCategoria;
    
    // Swagger, Preço do produto
    @Schema(description = "Preço do produto", example = "1999.99")
    private BigDecimal preco;
    
    // Swagger, Preço promocional do produto
    @Schema(description = "Preço promocional do produto", example = "1799.99")
    private BigDecimal precoPromocional;
    
    // Swagger, Estoque do produto
    @Schema(description = "Quantidade de unidades do produto no estoque", example = "150")
    private BigDecimal estoque;
    
    // Swagger, Fabricante do produto
    @Schema(description = "Nome do fabricante do produto", example = "Apple")
    private String fabricante;
    
    // Swagger, Produto ativo ou não
    @Schema(description = "Indica se o produto está ativo no sistema", example = "true")
    private Boolean ativo;
    
    // Swagger, Data de cadastro do produto
    @Schema(description = "Data de cadastro do produto", example = "2023-06-10")
    private LocalDate dataCadastro;
    
    // Swagger, Data da última atualização do produto
    @Schema(description = "Data da última atualização do produto", example = "2023-06-15")
    private LocalDate dataAtualizacao;
    
    // Swagger, Foto do produto
    @Schema(description = "Foto do produto", required = false)
    private Foto foto;

    // Construtores
    public ProdutoResponseDTO(Long id, String nome, String descricao, Long idCategoria, BigDecimal preco,
            BigDecimal precoPromocional, BigDecimal estoque, String fabricante, Boolean ativo, LocalDate dataCadastro,
            LocalDate dataAtualizacao, Foto foto) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.idCategoria = idCategoria;
        this.preco = preco;
        this.precoPromocional = precoPromocional;
        this.estoque = estoque;
        this.fabricante = fabricante;
        this.ativo = ativo;
        this.dataCadastro = dataCadastro;
        this.dataAtualizacao = dataAtualizacao;
        this.foto = foto;
    }
    
    public ProdutoResponseDTO() {}

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public BigDecimal getPrecoPromocional() {
        return precoPromocional;
    }

    public void setPrecoPromocional(BigDecimal precoPromocional) {
        this.precoPromocional = precoPromocional;
    }

    public BigDecimal getEstoque() {
        return estoque;
    }

    public void setEstoque(BigDecimal estoque) {
        this.estoque = estoque;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(LocalDate dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public LocalDate getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDate dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }
}
