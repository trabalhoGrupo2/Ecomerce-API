package org.serratec.h2.grupo2.DTO;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import io.swagger.v3.oas.annotations.media.Schema;

public class ClienteRequestDTO {
	
    // Swagger, Nome do cliente (não pode estar em branco)
    @NotNull(message = "O nome não pode estar em branco")
    @Schema(description = "Nome completo do cliente", example = "João da Silva", required = true)
	private String nome;

    // Swagger, CPF do cliente (não pode estar em branco)
    @NotNull(message = "O CPF não pode estar em branco")
    @Schema(description = "CPF do cliente", example = "123.456.789-00", required = true)
    private String cpf;

    // Swagger, Data de nascimento do cliente (não pode ser nula e deve ser uma data no passado)
    @Past
    @NotNull(message = "O campo não pode estar em branco")
    @Schema(description = "Data de nascimento do cliente", example = "1990-05-20", required = true)
    private LocalDate dataDeNascimento;

    // Swagger, Telefone do cliente (opcional)
    @Schema(description = "Telefone do cliente", example = "(21) 98765-4321")
    private String telefone;
    
    // Swagger, Email do cliente (opcional)
    @Schema(description = "Email do cliente", example = "joao.silva@email.com")
    private String email;
   
    // Swagger, Senha do cliente (opcional)
    @Schema(description = "Senha do cliente", example = "Senha@123")
    private String senha;

    public ClienteRequestDTO(@NotNull(message = "O nome não pode estar em branco") String nome,
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

