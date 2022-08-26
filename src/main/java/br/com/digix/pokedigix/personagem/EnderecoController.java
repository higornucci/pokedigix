package br.com.digix.pokedigix.personagem;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = { "/api/v1/enderecos" }, produces = { "application/json" })
public class EnderecoController {
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Operation(summary = "Deletar um Endereço pelo seu id")
    @ApiResponse(responseCode = "204")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> removerEnderecoId(@PathVariable Long id) {
        enderecoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
