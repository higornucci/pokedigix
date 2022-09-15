package br.com.digix.pokedigix.pokemon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.webjars.NotFoundException;

import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.ataque.AtaqueRepository;
import br.com.digix.pokedigix.ataque.AtaqueResponseDTO;
import br.com.digix.pokedigix.mappers.PokemonMapper;
import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRepository;
import br.com.digix.pokedigix.tipo.TipoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
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
  public ResponseEntity<PokemonResponseDTO> criarPokemon(
      @RequestBody PokemonRequestDTO novoPokemon)
      throws NivelPokemonInvalidoException, FelicidadeInvalidaException, LimiteDeTipoPokemonException,
      LimiteDeAtaquePokemonException, org.springframework.data.crossstore.ChangeSetPersister.NotFoundException {
      Pokemon pokemon = pokemonMapper.pokemonRequestParaPokemon(novoPokemon);
    pokemonRepository.save(pokemon);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(pokemonMapper.pokemonParaPokemonResponseDTO(pokemon));
  }

  @Operation(summary = "Atualizar um pokemon")
  @ApiResponse(responseCode = "200")
  @PutMapping(path = "/{id}", consumes = { "application/json" })
  public ResponseEntity<PokemonResponseDTO> atualizarPokemon(
      @RequestBody PokemonRequestDTO pokemonAtt,
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
    Collection<AtaqueResponseDTO> ataquesDTOs = new ArrayList<>();
    for (Ataque ataque : ataques) {
      AtaqueResponseDTO ataqueDTO = new AtaqueResponseDTO(
          ataque.getId(),
          ataque.getForca(),
          ataque.getAcuracia(),
          ataque.getPontosDePoder(),
          ataque.getCategoria(),
          ataque.getNome(),
          ataque.getDescricao(),
          new TipoResponseDTO(
              ataque.getTipo().getId(),
              ataque.getTipo().getNome()));
      ataquesDTOs.add(ataqueDTO);
    }
    Collection<TipoResponseDTO> tiposDTOs = new ArrayList<>();
    for (Tipo tipo : tipos) {
      TipoResponseDTO tipoDTO = new TipoResponseDTO(
          tipo.getId(),
          tipo.getNome());
      tiposDTOs.add(tipoDTO);
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

    return ResponseEntity.ok(
        new PokemonResponseDTO(
            alterarPokemon.getId(),
            alterarPokemon.getNome(),
            alterarPokemon.getAltura(),
            alterarPokemon.getPeso(),
            alterarPokemon.getGenero(),
            alterarPokemon.getNivel(),
            alterarPokemon.getNumeroPokedex(),
            alterarPokemon.getFelicidade(),
            ataquesDTOs,
            tiposDTOs));
  }

  @Operation(summary = "Buscar Pokemon pelo seu id do tipo")
  @ApiResponse(responseCode = "200", description = "Lista de Pokemons buscada pelo tipo")
  @GetMapping(path = "/tipo/{id}")
  public ResponseEntity<Collection<PokemonResponseDTO>> buscarPeloTipo(
      @PathVariable Long id) {
    Iterable<Pokemon> pokemons = pokemonRepository.buscarPorTipo(id);

    Collection<PokemonResponseDTO> pokemonsRetornados = new ArrayList<>();

    for (Pokemon pokemon : pokemons) {
      pokemonsRetornados.add(pokemonMapper.pokemonParaPokemonResponseDTO(pokemon));
    }
    return ResponseEntity.ok(pokemonsRetornados);
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
    return ResponseEntity.ok(pokemonMapper.pokemonsParaPokemonsResponseDTO(pokemons));
  }

}
