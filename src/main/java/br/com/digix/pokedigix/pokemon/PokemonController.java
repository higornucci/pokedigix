package br.com.digix.pokedigix.pokemon;

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
@RequestMapping(path = { "/api/v1/pokemons" }, produces = { "application/json" })
public class PokemonController {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Operation(summary = "Deletar um Pokemon pelo seu id")
    @ApiResponse(responseCode = "204")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> removerPokemonId(@PathVariable Long id) {
        pokemonRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Deletar um Pokemon pelo seu nome parcial ou completo")
    @ApiResponse(responseCode = "204")
    @DeleteMapping
    @Transactional
    public ResponseEntity<?> removerPokemon(@RequestParam(required = true) String termo) {
        pokemonRepository.deleteByNomeContaining(termo);
        return ResponseEntity.noContent().build();
    }
}