package br.com.digix.pokedigix.pokemon;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = { "/api/v1/pokemons" }, produces = { "application/json" })
public class PokemonController {

  @Autowired
  private PokemonService pokemonService;

  @Operation(summary = "Deletar um Pokemon pelo seu id")
  @ApiResponse(responseCode = "204")
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> removerPokemonId(@PathVariable Long id) {
    pokemonService.removerPorId(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Deletar um Pokemon pelo seu nome parcial ou completo")
  @ApiResponse(responseCode = "204")
  @DeleteMapping
  @Transactional
  public ResponseEntity<Void> removerPokemon(@RequestParam(required = true) String termo) {
    pokemonService.removerPeloNomeParcial(termo);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Criar um novo pokemon")
  @ApiResponse(responseCode = "201")
  @PostMapping(consumes = { "application/json" })
  public ResponseEntity<PokemonResponseDTO> criarPokemon(@RequestBody PokemonRequestDTO novoPokemon)
      throws NivelPokemonInvalidoException, FelicidadeInvalidaException, LimiteDeTipoPokemonException,
      LimiteDeAtaquePokemonException {
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(pokemonService.criar(novoPokemon));
  }

  @Operation(summary = "Atualizar um pokemon")
  @ApiResponse(responseCode = "200")
  @PutMapping(path = "/{id}", consumes = { "application/json" })
  public ResponseEntity<PokemonResponseDTO> atualizarPokemon(@RequestBody PokemonRequestDTO pokemonAtt,
      @PathVariable Long id)
      throws LimiteDeTipoPokemonException, LimiteDeAtaquePokemonException {

    return ResponseEntity.ok(pokemonService.atualizar(pokemonAtt, id));
  }

  @Operation(summary = "Buscar Pokemon pelo seu id do tipo")
  @ApiResponse(responseCode = "200", description = "Lista de Pokemons buscada pelo tipo")
  @GetMapping(path = "/tipo/{id}")
  public ResponseEntity<Collection<PokemonResponseDTO>> buscarPeloTipo(@PathVariable Long id) {
    return ResponseEntity.ok(pokemonService.buscarPokemonPeloIdDoTipo(id));
  }

  @Operation(summary = "Buscar Pokemon pelo seu nome parcial ou completo")
  @ApiResponse(responseCode = "200", description = "Lista de Pokemons buscada pelo seu nome (completo ou parcial)")
  @GetMapping
  public ResponseEntity<Collection<PokemonResponseDTO>> buscarPeloNome(
    @RequestParam(required = false, name = "pagina", defaultValue = "0") int pagina,
    @RequestParam(required = false, name = "quantidade", defaultValue = "4") int quantidade,
    @RequestParam(required = false, name = "campoOrdenacao", defaultValue = "nome") String campoOrdenacao,
    @RequestParam(required = false, name = "direcao", defaultValue = "ASC") String direcao,
    @RequestParam(required = false, name = "termo") String nome
      ) {
    return ResponseEntity.ok(pokemonService.buscarPeloNome(nome, pagina, quantidade, campoOrdenacao, direcao));
  }
}