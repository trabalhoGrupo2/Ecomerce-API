package org.serratec.h2.grupo2.controller;

import org.springframework.http.MediaType;
import java.io.IOException;
import org.springframework.http.HttpHeaders;
import java.util.List;

import org.serratec.h2.grupo2.DTO.ProdutoRequestDTO;
import org.serratec.h2.grupo2.DTO.ProdutoResponseDTO;
import org.serratec.h2.grupo2.domain.Foto;
import org.serratec.h2.grupo2.domain.Produto;
import org.serratec.h2.grupo2.repository.ProdutoRepository;
import org.serratec.h2.grupo2.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ContentDisposition;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ContentDisposition;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	// Injetar service
	@Autowired
	private ProdutoService service;
	
	// Injetar service
	@Autowired
	private ProdutoRepository produtoRepository;
	
	// GET: TODOS
	// ResponseEntity permite customizar o status HTTP
	@GetMapping
	public ResponseEntity<List<ProdutoResponseDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

	
	// GET: ID
	@GetMapping("/{id}")
	public ResponseEntity<ProdutoResponseDTO> pesquisar(@PathVariable Long id) {
		// Chama o método pesquisar e caso exista o ID vai retornar o status HTTP
		// ok com ProdutoResponse no corpo da resposta
        try {
            return ResponseEntity.ok(service.pesquisar(id));
        // Retorna error 404
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

	// POST: INSERIR
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public ProdutoResponseDTO inserir(@Valid @RequestBody ProdutoRequestDTO dto) {
//        return service.inserir(dto);
//    }
    
    
    
//    @PostMapping(value = "/com-foto", consumes = "multipart/form-data")
    @PostMapping(consumes = "multipart/form-data")
	public ResponseEntity<ProdutoResponseDTO> inserir(
	    @RequestPart("produto") ProdutoRequestDTO produtoJson,
	    @RequestPart("foto") MultipartFile fotoFile) throws IOException {

	    // Criar entidade Foto
	    Foto foto = new Foto();
	    foto.setDados(fotoFile.getBytes());
	    foto.setNome(fotoFile.getOriginalFilename());
	    foto.setTipo(fotoFile.getContentType());

	    produtoJson.setFoto(foto);

	    ProdutoResponseDTO response = service.inserir(produtoJson);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
    
    // PUT: ATUALIZAR
    // Multipart/form-data: aceita file no Postman
    @PutMapping(value = "/{id}", consumes = "multipart/form-data")
    public ResponseEntity<ProdutoResponseDTO> atualizar(
        @PathVariable Long id,
        @Valid @RequestPart("produto") ProdutoRequestDTO dto,
        @RequestPart(name = "foto", required = false) MultipartFile fotoFile
    ) throws IOException {
    	// Se eexistir foto no file, atualiza os dados no fotoDTO
        if (fotoFile != null && !fotoFile.isEmpty()) {
            Foto novaFoto = new Foto();
            novaFoto.setNome(fotoFile.getOriginalFilename());
            novaFoto.setTipo(fotoFile.getContentType());
            novaFoto.setDados(fotoFile.getBytes());
            dto.setFoto(novaFoto);
        }
        
        // Chama o método atualizar
        ProdutoResponseDTO atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    // DELETE: REMOVER
    @DeleteMapping("/{id}")
	public ResponseEntity<Void> remover(@PathVariable Long id) {
	    try {
	        service.remover(id);
	        return ResponseEntity.noContent().build(); // Retorna 204 No Content ao remover com sucesso
	    } catch (EntityNotFoundException e) {
	        return ResponseEntity.notFound().build(); // Retorna 404 caso não encontre o recurso
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Retorna 500 para erros inesperados
	    }
	}
    
    // Modificar, adicionar no Service todas as funções para apenas chamar no Controller
    @GetMapping("/{id}/foto")
    public ResponseEntity<byte[]> getFoto(@PathVariable Long id) {
        Produto produto = produtoRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Produto não encontrado"));

        Foto foto = produto.getFoto();
        if (foto == null || foto.getDados() == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto não encontrada");
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(foto.getTipo())); // ex: image/png
        headers.setContentLength(foto.getDados().length);
        headers.setContentDisposition(ContentDisposition.inline().filename(foto.getNome()).build());

        return new ResponseEntity<>(foto.getDados(), headers, HttpStatus.OK);
    }
		
    // GET: Itens em promoção
 	@GetMapping("/promocoes")
 	public ResponseEntity<List<ProdutoResponseDTO>> listarPromocoes() {
 	    List<ProdutoResponseDTO> promocoes = service.listarPromocoes();
 	    return ResponseEntity.ok(promocoes);
 	}
}