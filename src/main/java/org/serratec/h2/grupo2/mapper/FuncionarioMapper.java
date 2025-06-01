package org.serratec.h2.grupo2.mapper;

import java.util.ArrayList;
import java.util.List;
import org.serratec.h2.grupo2.DTO.funcionario.FuncionarioRequestDto;
import org.serratec.h2.grupo2.DTO.funcionario.FuncionarioResponseDto;
import org.serratec.h2.grupo2.domain.Funcionario;
import org.serratec.h2.grupo2.enuns.Cargo;
import org.springframework.stereotype.Component;

@Component
public class FuncionarioMapper {

	public Funcionario toFuncionario(FuncionarioRequestDto request) {
		Funcionario funcionario = new Funcionario();
		
		funcionario.setNome(request.getNome());
		funcionario.setCpf(request.getCpf());
		funcionario.setDataDeNascimento(request.getDataDeNascimento());
		funcionario.setCargo(request.getCargo());
		funcionario.setGenero(request.getGenero());
		funcionario.setTelefone(request.getTelefone());
		funcionario.getConta().setEmail(request.getEmail());
		funcionario.getConta().setSenha(request.getSenha());
		funcionario.getConta().setNivelAcesso(Cargo.getNivelAcesso(request.getCargo()));
		funcionario.getConta().setAtivo(true);
		
		return funcionario;
	}
	
	public FuncionarioResponseDto toResponse(Funcionario funcionario) {
		FuncionarioResponseDto response = new FuncionarioResponseDto();
		
		response.setCodigo(funcionario.getId());
		response.setNome(funcionario.getNome());
		response.setCpf(funcionario.getCpf());
		response.setDataDeNascimento(funcionario.getDataDeNascimento());
		response.setCargo(funcionario.getCargo());
		response.setGenero(funcionario.getGenero());
		response.setEmail(funcionario.getConta().getEmail());

		return response;
	}
	
	public List<FuncionarioResponseDto> toListResponse (List<Funcionario> listaFuncionarios) {
		List<FuncionarioResponseDto> listResponse = new ArrayList<> ();
			
		for(Funcionario f: listaFuncionarios) {
			listResponse.add(toResponse(f));}
			
		return listResponse;
	}
}
