package org.serratec.h2.grupo2.DTO;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.serratec.h2.grupo2.domain.Categoria;
import org.serratec.h2.grupo2.domain.Foto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoRequestDTO {

    // Swagger, Nome do produto (obrigatório)
    @NotBlank(message = "O nome deve ser preenchido")
    @Schema(description = "Nome do produto", example = "Smartphone X", required = true)
    private String nome;
    
    // Swagger, Descrição do produto (obrigatório, entre 3 e 255 caracteres)
    @Size(min = 3, max = 255, message = "O nome do produto deve ter entre 3 e 255 caracteres")
    @NotBlank(message = "A descrição deve ser preenchida")
    @Schema(description = "Descrição do produto", example = "Smartphone com tela de 6.5 polegadas e câmera de 12 MP", required = true)
    private String descricao;
    
    // Swagger, ID da categoria do produto (obrigatório)
    @NotNull(message = "O ID da categoria deve ser informado")
    @Schema(description = "ID da categoria do produto", example = "1", required = true)
    private Long idCategoria;
    
    // Swagger, Preço do produto (obrigatório e maior que zero)
    @NotNull(message = "O preço deve ser maior que zero")
    @Schema(description = "Preço do produto", example = "1999.99", required = true)
    private BigDecimal preco;
    
    // Swagger, Preço promocional do produto (opcional)
    @Schema(description = "Preço promocional do produto", example = "1799.99")
    private BigDecimal precoPromocional;
    
    // Swagger, Estoque do produto (opcional)
    @Schema(description = "Quantidade de unidades do produto no estoque", example = "150")
    private BigDecimal estoque;
    
    // Swagger, Fabricante do produto (opcional)
    @Schema(description = "Nome do fabricante do produto", example = "Apple")
    private String fabricante;
    
    // Swagger, Produto ativo ou não
    @Schema(description = "Indica se o produto está ativo no sistema", example = "true")
    private Boolean ativo;
    
    // Swagger, Data de cadastro do produto (opcional)
    @Schema(description = "Data de cadastro do produto", example = "2023-06-10")
    private LocalDate dataCadastro;
    
    // Swagger, Data da última atualização do produto (opcional)
    @Schema(description = "Data da última atualização do produto", example = "2023-06-15")
    private LocalDate dataAtualizacao;

    // Swagger, Foto do produto (opcional)
    @Schema(description = "Foto do produto", required = false)
    private Foto foto;
    
    // Getters e Setters

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
