package org.serratec.h2.grupo2.DTO;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public class ClienteRequestDTO {
	
	@NotNull(message = "O nome não pode estar em branco")
	private String nome;

	@NotNull(message = "O CPF não pode estar em branco")
    private String cpf;

	@Past
	@NotNull(message = "O campo não pode estar em branco")
    private LocalDate dataDeNascimento;

    private String telefone;
    
    private String email;
   
    private String senha;

	public ClienteRequestDTO
	       (@NotNull(message = "O nome não pode estar em branco") String nome,
			@NotNull(message = "O CPF não pode estar em branco") String cpf,
			@NotNull(message = "O campo não pode estar em branco") LocalDate dataDeNascimento, String telefone,
			String email, String senha) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.dataDeNascimento = dataDeNascimento;
		this.telefone = telefone;
		this.email = email;
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(LocalDate dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
    
    
}
