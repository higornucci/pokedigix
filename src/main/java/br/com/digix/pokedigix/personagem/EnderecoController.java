package br.com.digix.pokedigix.personagem;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = { "/api/v1/enderecos" }, produces = { "application/json" })
public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @GetMapping(path = "/cidade")
    public ResponseEntity<Collection<EnderecoResponseDTO>> buscarPorCidade(
            @RequestParam(required = false, name = "termo") String cidade) {
        Iterable<Endereco> enderecos;
        if (cidade != null) {
            enderecos = enderecoRepository.findByCidadeContaining(cidade);
        } else {
            enderecos = enderecoRepository.findAll();
        }
        Collection<EnderecoResponseDTO> enderecosRetornados = new ArrayList<>();

        for (Endereco endereco : enderecos) {
            enderecosRetornados
                    .add(new EnderecoResponseDTO(endereco.getId(), endereco.getCidade(), endereco.getRegiao()));
        }
        return ResponseEntity.ok(enderecosRetornados);
    }
    
    @GetMapping(path = "/regiao")
    public ResponseEntity<Collection<EnderecoResponseDTO>> buscarPorRegiao(
            @RequestParam(required = false, name = "termo") String regiao) {
        Iterable<Endereco> enderecos;
        if (regiao != null) {
            enderecos = enderecoRepository.findByRegiaoContaining(regiao);
        } else {
            enderecos = enderecoRepository.findAll();
        }
        Collection<EnderecoResponseDTO> enderecosRetornados = new ArrayList<>();

        for (Endereco endereco : enderecos) {
            enderecosRetornados
                    .add(new EnderecoResponseDTO(endereco.getId(), endereco.getCidade(), endereco.getRegiao()));
        }
        return ResponseEntity.ok(enderecosRetornados);

    }

}
