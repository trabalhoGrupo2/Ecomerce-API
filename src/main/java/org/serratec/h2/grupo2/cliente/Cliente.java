package org.serratec.h2.grupo2.cliente;

import java.time.LocalDate;

import org.serratec.h2.grupo2.Conta.domain.Conta;

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
public class Cliente {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String nome;
	
	private String cpf;
	
	private LocalDate dataDeNascimento;
	
	private String telefone;
	
	//private List<Pedido> pedidos;
	private Conta conta;
	
}
