package org.serratec.h2.grupo2.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

//import jakarta.persistence.*;  // Usar jakarta.persistence se for Spring Boot 3+

@Entity  // Anotação para marcar como entidade JPA
@Table(name = "endereco")  // Opcional, nome da tabela no banco
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Chave primária auto incrementada
    private Long id;

    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;

    @ManyToOne  // Muitos endereços podem pertencer a um cliente
    @JoinColumn(name = "cliente_id")  // FK na tabela endereco apontando para cliente
    private Cliente cliente;

    public Endereco() {
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}