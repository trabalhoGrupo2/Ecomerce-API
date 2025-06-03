package org.serratec.h2.grupo2.DTO;

public class FotoResponseDTO {
    private String nome;
    private String tipo;
    // Sem o campo de dados porque está dando erro no Postman, já que os dados também estavam
    // retornando ao usuário
    
	public FotoResponseDTO(String nome, String tipo) {
		super();
		this.nome = nome;
		this.tipo = tipo;
	}

	public FotoResponseDTO() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
    
    
}