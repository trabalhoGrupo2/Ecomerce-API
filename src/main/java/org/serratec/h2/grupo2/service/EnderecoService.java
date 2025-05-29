package org.serratec.h2.grupo2.service;

import java.util.Map;

import org.serratec.h2.grupo2.domain.Endereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EnderecoService {

    @Autowired
    private ViaCepService viaCepService;

    public Endereco preencherEnderecoPorCep(String cep) {
        Map<String, String> dadosCep = viaCepService.consultarCep(cep);

        Endereco endereco = new Endereco();
        endereco.setRua(dadosCep.get("logradouro"));
        endereco.setBairro(dadosCep.get("bairro"));
        endereco.setCidade(dadosCep.get("localidade"));
        endereco.setEstado(dadosCep.get("uf"));
        endereco.setCep(dadosCep.get("cep"));

        return endereco;
    }
}


