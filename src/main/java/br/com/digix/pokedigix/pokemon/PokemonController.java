package br.com.digix.pokedigix.pokemon;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.ataque.AtaqueRepository;
import br.com.digix.pokedigix.ataque.AtaqueResponseDTO;
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

  @Operation(summary = "Criar um novo pokemon")
  @ApiResponse(responseCode = "201")
  @PostMapping(consumes = { "application/json" })
  public ResponseEntity<PokemonResponseDTO> criarPokemon(
      @RequestBody PokemonRequestDTO novoPokemon)
      throws NivelPokemonInvalidoException, FelicidadeInvalidaException, LimiteDeTipoPokemonException, LimiteDeAtaquePokemonException {
    Collection<Tipo> tipos = new ArrayList<>();
    Collection<Ataque> ataques = new ArrayList<>();
    for (Long ataqueId : novoPokemon.getAtaquesIds()) {
      Ataque ataque = ataqueRepository.findById(ataqueId).get();
      ataques.add(ataque);
    }
    for (Long tipoId : novoPokemon.getTiposIds()) {
      Tipo tipo = tipoRepository.findById(tipoId).get();
      tipos.add(tipo);
    }
    Pokemon pokemon = new Pokemon(
        novoPokemon.getNome(),
        novoPokemon.getAltura(),
        novoPokemon.getPeso(),
        novoPokemon.getGenero(),
        novoPokemon.getNivel(),
        novoPokemon.getNumeroPokedex(),
        novoPokemon.getFelicidade(),
        tipos,
        ataques);
    pokemonRepository.save(pokemon);
    Collection<AtaqueResponseDTO> ataquesDTOs = new ArrayList<>();
    for(Ataque ataque : ataques) {
      AtaqueResponseDTO ataqueDTO = new AtaqueResponseDTO(
          ataque.getId(),
          ataque.getForca(),
          ataque.getAcuracia(),
          ataque.getPontosDePoder(),
          ataque.getCategoria(),
          ataque.getNome(),
          ataque.getDescricao(),
          new TipoResponseDTO(ataque.getTipo().getId(), ataque.getTipo().getNome()));
      ataquesDTOs.add(ataqueDTO);
    }
    Collection<TipoResponseDTO> tiposDTOs = new ArrayList<>();
    for(Tipo tipo : tipos) {
      TipoResponseDTO tipoDTO = new TipoResponseDTO(tipo.getId(), tipo.getNome());
      tiposDTOs.add(tipoDTO);
    }
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(
            new PokemonResponseDTO(
                pokemon.getId(),
                pokemon.getNome(),
                pokemon.getAltura(),
                pokemon.getPeso(),
                pokemon.getGenero(),
                pokemon.getNivel(),
                pokemon.getNumeroPokedex(),
                pokemon.getFelicidade(),
                ataquesDTOs,
                tiposDTOs));
  }
}
