package org.serratec.h2.grupo2.mapper;

import java.util.ArrayList;
import java.util.List;

import org.serratec.h2.grupo2.DTO.cliente.ClienteRequestDto;
import org.serratec.h2.grupo2.DTO.cliente.ClienteResponseDto;
import org.serratec.h2.grupo2.domain.Cliente;
import org.serratec.h2.grupo2.domain.Conta;
import org.serratec.h2.grupo2.domain.Endereco;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

	public Cliente toCliente(ClienteRequestDto request) {
	    Cliente cliente = new Cliente();
	    Conta conta = new Conta();
	    Endereco endereco = new Endereco();

	    cliente.setNome(request.getNome());
	    cliente.setCpf(request.getCpf());
	    cliente.setDataDeNascimento(request.getDataDeNascimento());
	    cliente.setTelefone(request.getTelefone());

	    conta.setEmail(request.getEmail());
	    conta.setSenha(request.getSenha());
	    cliente.setConta(conta);

	    endereco.setCep(request.getCep());
	    endereco.setNumero(request.getNumero());
	    cliente.setEndereco(endereco);

	    return cliente;
	}
	
	public ClienteResponseDto toResponse(Cliente cliente) {
		ClienteResponseDto response = new ClienteResponseDto();
		
		response.setCodigo(cliente.getId());
		response.setNome(cliente.getNome());
		response.setCpf(cliente.getCpf());
		response.setDataDeNascimento(cliente.getDataDeNascimento());
		response.setEmail(cliente.getConta().getEmail());
		response.getEndereco().setRua(cliente.getEndereco().getRua());
		response.getEndereco().setNumero(cliente.getEndereco().getNumero());
		response.getEndereco().setBairro(cliente.getEndereco().getBairro());
		response.getEndereco().setCidade(cliente.getEndereco().getCidade());
		response.getEndereco().setEstado(cliente.getEndereco().getEstado());
		response.getEndereco().setCep(cliente.getEndereco().getCep());

		return response;
	}
	
	public List<ClienteResponseDto> toListResponse (List<Cliente> listaClientes) {
		List<ClienteResponseDto> listResponse = new ArrayList<> ();
			
		for(Cliente c: listaClientes) {
			listResponse.add(toResponse(c));}
			
		return listResponse;
	}
}
