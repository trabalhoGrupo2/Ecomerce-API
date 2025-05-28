package org.serratec.h2.grupo2.categoria;

public class CategoriaException extends RuntimeException {
	public CategoriaException(Long id) {
		super("Categoria não encontrada com o ID: " + id);
	}

	public CategoriaException(String mensagem) {
		super("Categoria já existente!" + mensagem);
	}
}
