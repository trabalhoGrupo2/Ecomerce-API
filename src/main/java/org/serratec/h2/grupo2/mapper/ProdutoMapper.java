package org.serratec.h2.grupo2.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.serratec.h2.grupo2.DTO.ProdutoRequestDTO;
import org.serratec.h2.grupo2.DTO.ProdutoResponseDTO;
import org.serratec.h2.grupo2.domain.Produto;
import org.springframework.stereotype.Component;

@Component
public class ProdutoMapper {

	public Produto toProduto(ProdutoRequestDTO request) {
		Produto produto = new Produto();

		produto.setNome(request.getNome());
		
		produto.setDescricao(request.getDescricao());
		
//		produto.getCategoria().setId(request.getId());
		
		produto.setPreco(request.getPreco());
		produto.setPrecoPromocional(request.getPrecoPromocional());
		produto.setEstoque(request.getEstoque());
		produto.setFabricante(request.getFabricante());
		produto.setAtivo(true);
		produto.setDataCadastro(LocalDate.now());
		produto.setDataAtualizacao(LocalDate.now());
		produto.setFoto(request.getFoto());
		return produto;
	}
	
	public ProdutoResponseDTO toResponse(Produto produto) {
		ProdutoResponseDTO response = new ProdutoResponseDTO();
		response.setId(produto.getId());
		response.setNome(produto.getNome());
		response.setDescricao(produto.getDescricao());
		response.setId(produto.getCategoria().getId()); // Voltar
		response.setPreco(produto.getPreco());
		response.setPrecoPromocional(produto.getPrecoPromocional());
		response.setEstoque(produto.getEstoque());
		response.setFabricante(produto.getFabricante());
		response.setAtivo(produto.getAtivo());
		response.setDataCadastro(produto.getDataCadastro());
		response.setDataAtualizacao(produto.getDataAtualizacao());
		response.setFoto(produto.getFoto());
		return response;
	}
	
	public List<ProdutoResponseDTO> toListResponse (List<Produto> listaProdutos) {
		List<ProdutoResponseDTO> listResponse = new ArrayList<> ();
			
		for(Produto p: listaProdutos) {
			listResponse.add(toResponse(p));}
			
		return listResponse;
	}
}