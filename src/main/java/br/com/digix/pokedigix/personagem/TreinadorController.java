package br.com.digix.pokedigix.personagem;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = { "/api/v1/treinadores" }, produces = { "application/json" })
public class TreinadorController {
    @Autowired
    private TreinadorRepository treinadorRepository;

    @Operation(summary = "Deletar um Treinador pelo seu id")
    @ApiResponse(responseCode = "204")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> removerTreinadorId(@PathVariable Long id) {
        treinadorRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletar um Treinador pelo seu nome parcial ou completo")
    @ApiResponse(responseCode = "204")
    @DeleteMapping
    @Transactional
    public ResponseEntity<?> removerTreinador(@RequestParam(required = true) String termo) {
        treinadorRepository.deleteByNomeContaining(termo);
        return ResponseEntity.noContent().build();
    }
}
