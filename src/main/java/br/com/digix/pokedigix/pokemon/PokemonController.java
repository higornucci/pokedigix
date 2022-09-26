package br.com.digix.pokedigix.pokemon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

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
import org.webjars.NotFoundException;

import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.ataque.AtaqueRepository;
import br.com.digix.pokedigix.mappers.PokemonMapper;
import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = { "/api/v1/pokemons" }, produces = { "application/json" })
public class PokemonController {

  @Autowired
  private PokemonRepository pokemonRepository;

  @Autowired
  private AtaqueRepository ataqueRepository;

  @Autowired
  private TipoRepository tipoRepository;

  @Autowired
  private PokemonMapper pokemonMapper;

  @Operation(summary = "Deletar um Pokemon pelo seu id")
  @ApiResponse(responseCode = "204")
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> removerPokemonId(@PathVariable Long id) {
    pokemonRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Deletar um Pokemon pelo seu nome parcial ou completo")
  @ApiResponse(responseCode = "204")
  @DeleteMapping
  @Transactional
  public ResponseEntity<Void> removerPokemon(@RequestParam(required = true) String termo) {
    pokemonRepository.deleteByNomeContaining(termo);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Criar um novo pokemon")
  @ApiResponse(responseCode = "201")
  @PostMapping(consumes = { "application/json" })
  public ResponseEntity<PokemonResponseDTO> criarPokemon(@RequestBody PokemonRequestDTO novoPokemon)
      throws NivelPokemonInvalidoException, FelicidadeInvalidaException, LimiteDeTipoPokemonException,
      LimiteDeAtaquePokemonException {
    Pokemon pokemon = pokemonMapper.pokemonRequestParaPokemon(novoPokemon);
    pokemonRepository.save(pokemon);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(pokemonMapper.pokemonParaPokemonResponse(pokemon));
  }

  @Operation(summary = "Atualizar um pokemon")
  @ApiResponse(responseCode = "200")
  @PutMapping(path = "/{id}", consumes = { "application/json" })
  public ResponseEntity<PokemonResponseDTO> atualizarPokemon(@RequestBody PokemonRequestDTO pokemonAtt,
      @PathVariable Long id)
      throws LimiteDeTipoPokemonException, LimiteDeAtaquePokemonException {
    Collection<Tipo> tipos = new ArrayList<>();
    Collection<Ataque> ataques = new ArrayList<>();

    for (Long ataqueId : pokemonAtt.getAtaquesIds()) {
      Optional<Ataque> ataqueOptional = ataqueRepository.findById(ataqueId);
      if (ataqueOptional.isEmpty()) {
        throw new NotFoundException(null);
      }
      Ataque ataque = ataqueOptional.get();
      ataques.add(ataque);
    }

    for (Long tipoId : pokemonAtt.getTiposIds()) {
      Optional<Tipo> tipoOptional = tipoRepository.findById(tipoId);
      if (tipoOptional.isEmpty()) {
        throw new NotFoundException(null);
      }
      Tipo tipo = tipoOptional.get();
      tipos.add(tipo);
    }
    Optional<Pokemon> pokemonOptional = pokemonRepository.findById(id);
    if (pokemonOptional.isEmpty()) {
      throw new NotFoundException(null);
    }
    Pokemon alterarPokemon = pokemonOptional.get();
    alterarPokemon.setNome(pokemonAtt.getNome());
    alterarPokemon.setAltura(pokemonAtt.getAltura());
    alterarPokemon.setPeso(pokemonAtt.getPeso());
    alterarPokemon.setGenero(pokemonAtt.getGenero());
    alterarPokemon.setNivel(pokemonAtt.getNivel());
    alterarPokemon.setNumeroPokedex(pokemonAtt.getNumeroPokedex());
    alterarPokemon.setFelicidade(pokemonAtt.getFelicidade());
    alterarPokemon.setAtaques(ataques);
    alterarPokemon.setTipos(tipos);
    pokemonRepository.save(alterarPokemon);
    return ResponseEntity.ok(pokemonMapper.pokemonParaPokemonResponse(alterarPokemon));
  }

  @Operation(summary = "Buscar Pokemon pelo seu id do tipo")
  @ApiResponse(responseCode = "200", description = "Lista de Pokemons buscada pelo tipo")
  @GetMapping(path = "/tipo/{id}")
  public ResponseEntity<Collection<PokemonResponseDTO>> buscarPeloTipo(@PathVariable Long id) {
    Collection<Pokemon> pokemons = pokemonRepository.buscarPorTipo(id);
    return ResponseEntity.ok(pokemonMapper.pokemonsParaPokemonsResponses(pokemons));
  }

  @Operation(summary = "Buscar Pokemon pelo seu nome parcial ou completo")
  @ApiResponse(responseCode = "200", description = "Lista de Pokemons buscada pelo seu nome (completo ou parcial)")
  @GetMapping
  public ResponseEntity<Collection<PokemonResponseDTO>> buscarPeloNome(
      @RequestParam(required = false, name = "termo") String nome) {
    Collection<Pokemon> pokemons;
    if (nome != null) {
      pokemons = pokemonRepository.findByNomeContaining(nome);
    } else {
      pokemons = (Collection<Pokemon>) pokemonRepository.findAll();
    }
    return ResponseEntity.ok(pokemonMapper.pokemonsParaPokemonsResponses(pokemons));
  }
}