package org.serratec.h2.grupo2.controller;

import java.util.List;
import org.serratec.h2.grupo2.DTO.cliente.ClienteRequestDto;
import org.serratec.h2.grupo2.DTO.cliente.ClienteResponseDto;
import org.serratec.h2.grupo2.DTO.cliente.ClienteUpdateDto;
import org.serratec.h2.grupo2.DTO.cliente.quantidadeClientes.QuantidadeEstadoDto;
import org.serratec.h2.grupo2.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;



@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    private ClienteService service;

    //endpoint p cadastro
    @PostMapping("/cadastro")
    public ClienteResponseDto cadastrar(@Valid @RequestBody ClienteRequestDto request) {
        return service.cadastrarCliente(request);
    }

    //atualização cadastro
    @PutMapping("/atualizarCadastro")
    public ClienteResponseDto atualizacaoCadastro(@Valid @RequestBody ClienteRequestDto request) {
        return service.atualizacaoCadastro(request);
    }

    //atualização completa c base no id
    @PutMapping("/atualizarCliente/{id}")
    public ClienteResponseDto atualizacaoCompleta(@PathVariable Long id, @Valid @RequestBody ClienteRequestDto request) {
        return service.atualizacaoCadastroClientePorFuncionario(id, request);
    }

    //atualização parcial de dados
    @PatchMapping("/atualizacaoParcial")
    public ClienteResponseDto atualizacaoParcial(@RequestBody ClienteUpdateDto request) {
        return service.atualizacaoParcial(request);
    }


    //desativa conta
    @PatchMapping("/desativarConta/{id}")
    public ClienteResponseDto desativarConta(@PathVariable Long id) {
        return service.desativarConta(id);
    }

    //ativa novamente a conta
    @PatchMapping("/ativarConta/{id}")
    public ClienteResponseDto ativarConta(@PathVariable Long id) {
        return service.tivarConta(id);
    }

    //ativação via email e token
    @PatchMapping("/ativarContaEmail")
    public ResponseEntity<String> ativarConta(@RequestParam String email, @RequestParam String token) {
        return service.ativarConta(email, token);
    }

    //lista cliente cadastro
    @GetMapping("/listarClientes")
   
    public List<ClienteResponseDto> listarTodos() {
        return service.listarTodos();
    }

    
    //busca por id
    @GetMapping("/buscarPorId/{id}")
    public ClienteResponseDto buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    
    //busca por nome
    @GetMapping("/buscarPorNome/{nome}")
    public List<ClienteResponseDto> buscarPorNome(@PathVariable String nome) {
        return service.buscarNome(nome);
    }
 
    
    //busca cliente por cidade
    @GetMapping("/buscarPorCidade/{cidade}")
    public List<ClienteResponseDto> buscarPorCidade(@PathVariable String cidade) {
        return service.pesquisaPorCidade(cidade);
    }

    
    //busca por estado
    @GetMapping("/buscarPorEstado/{estado}")
    public List<ClienteResponseDto> buscarPorEstado(@PathVariable String estado) {
        return service.pesquisaPorEstado(estado);
    }

    
    //lista quantidade
    @GetMapping("/quantidadePorEstadoECidade")
   
    public List<QuantidadeEstadoDto> quantidadeClientesPorEstadoECidade() {
        return service.listarQuantidadeClientes();
    }

    
    //excluui o cliente permanentemente
    @DeleteMapping("/excluirCliente/{id}")
   
    public ResponseEntity<String> excluirCliente(@PathVariable Long id) {
        return service.excluirCliente(id);
    }
}