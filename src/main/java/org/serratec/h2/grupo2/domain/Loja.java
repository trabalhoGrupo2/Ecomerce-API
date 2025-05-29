package org.serratec.h2.grupo2.domain;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Loja {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String descricao;
	
	private String emailContato;
	
	@ManyToMany
	@JoinTable(name="loja_produto",
	joinColumns = @JoinColumn(name="id_loja"),
	inverseJoinColumns = @JoinColumn(name="id_produto"))
	private List<Produto> produtosCadastrados;
	
	//private Endereco endereco;
	
	//private List<pedidos> pedidos;
}
