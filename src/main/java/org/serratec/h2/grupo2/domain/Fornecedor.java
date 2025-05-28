package org.serratec.h2.grupo2.domain;


import org.serratec.h2.grupo2.enuns.TipoPessoa;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Fornecedor {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String cpf;
	
	private Conta conta;
	
	private TipoPessoa pessoa;
	
	private String cpfOuCnpj;
	
	//private Endereco endereco;

}
