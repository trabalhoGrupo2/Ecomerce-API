package org.serratec.h2.grupo2.DTO;

public class PedidoDTO {
	private String descricao;
	private Double valorTotal;
	
	

	public PedidoDTO(String descricao, Double valorTotal) {
		super();
		this.descricao = descricao;
		this.valorTotal = valorTotal;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public void setValorTotal(Double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public Object getDescricao() {
		
		return null;
	}

	public double getValorTotal() {
		
		return 0;
	}

	}

	

