package org.serratec.h2.grupo2.DTO;

import java.time.LocalDate;

import org.serratec.h2.grupo2.domain.Conta;
import org.serratec.h2.grupo2.enuns.Genero;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import io.swagger.v3.oas.annotations.media.Schema;

public class FuncionarioRequestDTO {

    // Swagger, Nome completo do funcionário (obrigatório)
    @NotNull(message = "O nome não pode estar em branco")
    @Schema(description = "Nome completo do funcionário", example = "Carlos Souza", required = true)
	private String nome;
	
    // Swagger, CPF do funcionário (obrigatório)
    @NotNull(message = "O CPF não pode estar em branco")
    @Schema(description = "CPF do funcionário", example = "123.456.789-00", required = true)
	private String cpf;
	
    // Swagger, Data de nascimento (obrigatório e no passado)
    @Past
    @NotNull(message = "O campo não pode estar em branco")
    @Schema(description = "Data de nascimento do funcionário", example = "1990-04-10", required = true)
	private LocalDate dataDeNascimento;
	
    // Swagger, Gênero do funcionário (obrigatório)
    @NotNull(message = "O genero não pode estar em branco")
    @Schema(description = "Gênero do funcionário", example = "MASCULINO", required = true)
	private Genero genero;
	
    // Swagger, Telefone do funcionário (obrigatório)
    @NotNull(message = "O telefone não pode estar em branco")
    @Schema(description = "Telefone do funcionário", example = "(21) 98765-4321", required = true)
	private String telefone;
	
    // Swagger, Conta bancária associada ao funcionário (obrigatório)
    @NotNull(message = "A conta não pode estar em branco")
    @Schema(description = "Conta bancária do funcionário", required = true)
	private Conta conta;

    public FuncionarioRequestDTO(@NotNull(message = "O nome não pode estar em branco") String nome,
			@NotNull(message = "O CPF não pode estar em branco") String cpf,
			@Past @NotNull(message = "O campo não pode estar em branco") LocalDate dataDeNascimento,
			@NotNull(message = "O genero não pode estar em branco") Genero genero,
			@NotNull(message = "O telefone não pode estar em branco") String telefone,
			@NotNull(message = "A conta não pode estar em branco") Conta conta) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.dataDeNascimento = dataDeNascimento;
		this.genero = genero;
		this.telefone = telefone;
		this.conta = conta;
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

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
}

