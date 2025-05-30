package org.serratec.h2.grupo2.controller;


import java.util.List;

import org.serratec.h2.grupo2.DTO.PedidoDTO;
import org.serratec.h2.grupo2.domain.Pedido;
import org.serratec.h2.grupo2.repository.PedidoRepository;
import org.serratec.h2.grupo2.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;


@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    
    @Autowired
    private PedidoRepository repository;
    

    // Inserir pedido
    @PostMapping
    public ResponseEntity<Pedido> criarPedido( @RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoService.criarPedido(pedidoDTO);
        return ResponseEntity.ok(pedido);
    }
    // Editar pedido 
    @PutMapping("/{id}")
    public ResponseEntity<Pedido> editarPedido(@PathVariable Long id, @RequestBody PedidoDTO pedidoDTO) {
        Pedido pedido = pedidoService.editarPedido(id, pedidoDTO);
        return ResponseEntity.ok(pedido);
    }
    // Alterar status do pedido
    @PatchMapping("/{id}/status")
    public ResponseEntity<Pedido> alterarStatus(@PathVariable Long id, @RequestParam String status) {
        Pedido pedido = pedidoService.alterarStatus(id, status);
        return ResponseEntity.ok(pedido);
    }
    // Buscar pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscarPorId(@PathVariable Long id) {
        Pedido pedido = pedidoService.buscarPorId(id);
        return ResponseEntity.ok(pedido);
    }
    // Listar todos os pedidos
    @GetMapping
    public ResponseEntity<List<Pedido>> listarTodos() {
        List<Pedido> pedidos = pedidoService.listarTodos();
        return ResponseEntity.ok(pedidos);
    }
    
    @DeleteMapping("/{id}")
    public void remover(@PathVariable Long id) {
        Pedido pedido = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pedido com ID " + id + " não encontrado."));
    	 repository.deleteById(id);
        }
    
    @GetMapping("/por-cliente")
    public ResponseEntity<List<Pedido>> listarPorCliente(@RequestParam Long clienteId) {
        List<Pedido> pedidos = pedidoService.listarPorClienteId(clienteId);
        return ResponseEntity.ok(pedidos);
    }
    
}



