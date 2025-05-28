package org.serratec.h2.grupo2.loja;

import java.util.List;

import org.serratec.h2.grupo2.domain.Produto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
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
	
	private Email emailContato;
	
	private List<Produto> produtosCadastrados;
	
	//private Endereco endereco;
	
	//private List<pedidos> pedidos;
}
