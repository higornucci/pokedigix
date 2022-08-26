package br.com.digix.pokedigix.personagem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = { "/api/v1/enderecos" }, produces = { "application/json" })

public class EnderecoController {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Operation(summary = "Atualizar o Endereço")
    @ApiResponse(responseCode = "200")
    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<EnderecoResponseDTO> atualizarEndereco(@RequestBody EnderecoUpdateDTO enderecoRequestDTO,
            @PathVariable Long id) {
        Endereco endereco = enderecoRepository.findById(id).get();
        endereco.setRegiao(enderecoRequestDTO.getRegiao());
        endereco.setCidade(enderecoRequestDTO.getCidade());

        enderecoRepository.save(endereco);

        return ResponseEntity.ok(new EnderecoResponseDTO(
                endereco.getRegiao(),
                endereco.getCidade(),
                endereco.getId()));
    }
}
