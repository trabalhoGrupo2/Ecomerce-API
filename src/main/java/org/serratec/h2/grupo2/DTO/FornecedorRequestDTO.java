package org.serratec.h2.grupo2.DTO;

import org.serratec.h2.grupo2.domain.Conta;
import org.serratec.h2.grupo2.enuns.TipoPessoa;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;

public class FornecedorRequestDTO {
	
    // Swagger, Nome do fornecedor (não pode estar em branco)
    @NotNull(message = "O nome não pode estar em branco")
    @Schema(description = "Nome do fornecedor", example = "Fornecedor ABC", required = true)
	private String nome;
	
    // Swagger, CPF do fornecedor (não pode estar em branco)
    @NotNull(message = "O CPF não pode estar em branco")
    @Schema(description = "CPF do fornecedor", example = "123.456.789-00", required = true)
    private String cpf;
	
    // Swagger, Conta associada ao fornecedor (não pode estar em branco)
    @NotNull(message = "A conta não pode estar em branco")
    @Schema(description = "Conta bancária do fornecedor", required = true)
	private Conta conta;
	
    // Swagger, Tipo de pessoa (Física ou Jurídica) do fornecedor
    @NotNull(message = "O campo não pode estar em branco")
    @Schema(description = "Tipo de pessoa (Física ou Jurídica)", example = "FÍSICA", required = true)
	private TipoPessoa pessoa;
	
    // Swagger, CPF ou CNPJ do fornecedor (não pode estar em branco)
    @NotNull(message = "O campo não pode estar em branco")
    @Schema(description = "CPF ou CNPJ do fornecedor", example = "123.456.789-00", required = true)
	private String cpfOuCnpj;

    public FornecedorRequestDTO(@NotNull(message = "O nome não pode estar em branco") String nome,
			@NotNull(message = "O CPF não pode estar em branco") String cpf,
			@NotNull(message = "A conta não pode estar em branco") Conta conta,
			@NotNull(message = "O campo não pode estar em branco") TipoPessoa pessoa,
			@NotNull(message = "O campo não pode estar em branco") String cpfOuCnpj) {
		super();
		this.nome = nome;
		this.cpf = cpf;
		this.conta = conta;
		this.pessoa = pessoa;
		this.cpfOuCnpj = cpfOuCnpj;
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

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public TipoPessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(TipoPessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}
}
