package org.serratec.h2.grupo2.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.serratec.h2.grupo2.loja.Loja;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="produto")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="nome", nullable=false, length=50)
	private String nome;
	
	@Column(name="descricao", nullable=false, length=255)
	private String descricao;
	
//	private Categoria categoria;
	
	@DecimalMin(value="0", message="O preço não pode ser menor que R$ {value}.00")
	private BigDecimal preco;
	
	@DecimalMin(value="0", message="O preço não pode ser menor que R$ {value}.00")
	private BigDecimal precoPromocional;
	
	@DecimalMin(value="0", message="O estoque não pode ser negativo")
	private BigDecimal estoque;
	
	private Loja loja;
	
	@Column(name="fabricante", nullable=false, length=50)
	private String fabricante;
	
	private Boolean ativo;
	
	private LocalDate dataCadastro;
	
	private LocalDate dataAtualizacao;

	// Relação entre foto e produto
	@OneToOne(mappedBy = "produto", cascade = CascadeType.ALL)
	private Foto foto;
}
