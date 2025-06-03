package org.serratec.h2.grupo2.controller;

import org.serratec.h2.grupo2.DTO.conta.ContaRequestDto;
import org.serratec.h2.grupo2.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;       //swagger
import io.swagger.v3.oas.annotations.tags.Tag;        //swagger
import io.swagger.v3.oas.annotations.responses.ApiResponse;  //swagger
import io.swagger.v3.oas.annotations.responses.ApiResponses; //swagger
import io.swagger.v3.oas.annotations.media.Content;           //swagger
import io.swagger.v3.oas.annotations.media.Schema;            //swagger

@RestController
@RequestMapping("/login")
@Tag(name = "Autenticação", description = "Endpoints para login e autenticação")  //swagger
public class ContaController {
	
	@Autowired
	private ContaService service;

	// Swagger, Realiza o login da conta com os dados fornecidos
	@Operation(summary = "Login de usuário", description = "Realiza o login da conta com os dados fornecidos")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Login efetuado com sucesso",
			content = @Content(mediaType = "text/plain")),
		@ApiResponse(responseCode = "401", description = "Credenciais inválidas")
	})
	@PostMapping
	public String login(@RequestBody ContaRequestDto conta) {
		return service.login(conta);
	}
}
