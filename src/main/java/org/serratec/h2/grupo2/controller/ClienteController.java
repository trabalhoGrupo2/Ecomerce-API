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

    //CADASTRO DE CLIENTE - SOMENTE O PRÓPRIO CLIENTE PODE SE CADASTRAR
    @PostMapping("/cadastro")
    public ClienteResponseDto cadastrar(@Valid @RequestBody ClienteRequestDto request) {
        return service.cadastrarCliente(request);
    }

    //CLIENTE FAZ A ATUALIZAÇÃO DA PRÓPRIA CONTA
    @PutMapping("/atualizarCadastro")
    public ClienteResponseDto atualizacaoCadastro(@Valid @RequestBody ClienteRequestDto request) {
        return service.atualizacaoCadastro(request);
    }

    //FUNCIONÁRIO ATUALIZA A CONTA DO, CLIENTE
    @PutMapping("/atualizarCliente/{id}")
    public ClienteResponseDto atualizacaoCompleta(@PathVariable Long id, @Valid @RequestBody ClienteRequestDto request) {
        return service.atualizacaoCadastroFuncionario(id, request);
    }

    //CLIENTE FAZ UMA ATUALIZAÇÃO PARCIAL DOS DADOS
    @PatchMapping("/atualizacaoParcial")
    public ClienteResponseDto atualizacaoParcial(@RequestBody ClienteUpdateDto request) {
        return service.atualizacaoParcial(request);
    }

    //CLIENTE ATUALIZA O ENDEREÇO
    
    //FUNCIONÁRIO ATUALIZA O ENDEREÇO DO CLIENTE

    //FUNCIONÁRIO DESATIVA A CONTA DO CLIENTE
    @PatchMapping("/desativarConta/{id}")
    public ClienteResponseDto desativarConta(@PathVariable Long id) {
        return service.desativarConta(id);
    }

    //FUNCIONÁRIO FAZ A ATIVAÇÃO DE UMA CONTA CLIENTE
    @PatchMapping("/ativarConta/{id}")
    public ClienteResponseDto ativarConta(@PathVariable Long id) {
        return service.tivarConta(id);
    }

    //CLIENTE ATIVA A CONTA VIA TOKEN
    @PatchMapping("/ativarContaEmail/{email}/{token}")
    public ResponseEntity<String> ativarConta(@PathVariable String email, @PathVariable String token) {
        return service.ativarConta(email, token);
    }

    //LISTA TODOS OS CLIENTES CADASTRADOS
    @GetMapping("/listarClientes")
    public List<ClienteResponseDto> listarTodos() {
        return service.listarTodos();
    }

    //BUSCA CLIENTE PELO ID
    @GetMapping("/buscarPorId/{id}")
    public ClienteResponseDto buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    
    //LISTA CLIENTES PELO NOME
    @GetMapping("/buscarPorNome/{nome}")
    public List<ClienteResponseDto> buscarPorNome(@PathVariable String nome) {
        return service.buscarNome(nome);
    }
 
    //LISTA CLIENTES PELA CIDADE
    @GetMapping("/buscarPorCidade/{cidade}")
    public List<ClienteResponseDto> buscarPorCidade(@PathVariable String cidade) {
        return service.pesquisaPorCidade(cidade);
    }
  
    //LISTA CLIENTES PELO ESTADO
    @GetMapping("/buscarPorEstado/{estado}")
    public List<ClienteResponseDto> buscarPorEstado(@PathVariable String estado) {
        return service.pesquisaPorEstado(estado);
    }

    //RELATÓRIO DE QUANTO CLIENTES EXISTEM POR ESTADO/CIDADE
    @GetMapping("/quantidadePorEstadoECidade")
    public List<QuantidadeEstadoDto> quantidadeClientesPorEstadoECidade() {
        return service.listarQuantidadeClientes();
    }
    
    //EXCLUÍ CLIENTE PERMANENTEMENTE
    @DeleteMapping("/excluirCliente/{id}")
    public ResponseEntity<String> excluirCliente(@PathVariable Long id) {
        return service.excluirCliente(id);
    }
}