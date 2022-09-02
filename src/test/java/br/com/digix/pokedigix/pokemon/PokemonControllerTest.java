package br.com.digix.pokedigix.pokemon;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.digix.pokedigix.PokedigixApplication;
import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.ataque.AtaqueBuilder;
import br.com.digix.pokedigix.ataque.AtaqueRepository;
import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRepository;
import br.com.digix.pokedigix.utils.JsonUtil;
import java.util.ArrayList;
import java.util.Collection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(
  webEnvironment = WebEnvironment.RANDOM_PORT,
  classes = PokedigixApplication.class
)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class PokemonControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private PokemonRepository pokemonRepository;

  @Autowired
  private TipoRepository tipoRepository;

  @Autowired
  private AtaqueRepository ataqueRepository;

  @AfterEach
  void resetdb2() {
    pokemonRepository.deleteAll();
    ataqueRepository.deleteAll();
    tipoRepository.deleteAll();
  }

  @BeforeEach
  public void resetDb() {
    pokemonRepository.deleteAll();
    ataqueRepository.deleteAll();
    tipoRepository.deleteAll();
  }

  @Test
  public void deve_criar_um_pokemon() throws Exception {
    String tipao = "Agua";

    Tipo tipo = new Tipo(tipao);
    tipoRepository.save(tipo);
    Ataque ataque = new AtaqueBuilder().comTipo(tipo).construir();
    ataqueRepository.save(ataque);
    Pokemon pokemon = new PokemonBuilder()
      .comAtaque(ataque)
      .comTipo(tipo)
      .construir();
    pokemonRepository.save(pokemon);
    Collection<Long> ataquesIds = new ArrayList<>();
    ataquesIds.add(ataque.getId());
    Collection<Long> tiposIds = new ArrayList<>();
    tiposIds.add(tipo.getId());

    PokemonRequestDTO pokemonRequestDTO = new PokemonRequestDTO(
      pokemon.getNome(),
      pokemon.getAltura(),
      pokemon.getPeso(),
      pokemon.getGenero(),
      pokemon.getNivel(),
      pokemon.getNumeroPokedex(),
      pokemon.getFelicidade(),
      tiposIds,
      ataquesIds
    );

    mvc
      .perform(
        post("/api/v1/pokemons")
          .contentType(MediaType.APPLICATION_JSON)
          .content(JsonUtil.toJson(pokemonRequestDTO))
      )
      .andExpect(status().isCreated());

    Iterable<Pokemon> pokemonsEncontrados = pokemonRepository.findAll();

    assertThat(pokemonsEncontrados)
      .extracting("nome")
      .containsOnly(pokemon.getNome());
    assertThat(pokemonsEncontrados)
      .extracting("altura")
      .containsOnly(pokemon.getAltura());
    assertThat(pokemonsEncontrados)
      .extracting("peso")
      .containsOnly(pokemon.getPeso());
    assertThat(pokemonsEncontrados)
      .extracting("genero")
      .containsOnly(pokemon.getGenero());
    assertThat(pokemonsEncontrados)
      .extracting("nivel")
      .containsOnly(pokemon.getNivel());
    assertThat(pokemonsEncontrados)
      .extracting("numeroPokedex")
      .containsOnly(pokemon.getNumeroPokedex());
    assertThat(pokemonsEncontrados)
      .extracting("felicidade")
      .containsOnly(pokemon.getFelicidade());
  }

  @Test
  public void deve_atualizar_um_Pokemon() throws Exception {
    //Arrange
    String nomeAlterado = "Eeve";
    String tipao = "Fogo";

    Tipo tipo = new Tipo(tipao);
    tipoRepository.save(tipo);
    Ataque ataque = new AtaqueBuilder().comTipo(tipo).construir();
    ataqueRepository.save(ataque);
    Pokemon pokemon = new PokemonBuilder()
      .comAtaque(ataque)
      .comTipo(tipo)
      .construir();
    pokemonRepository.save(pokemon);
    Collection<Long> ataquesIds = new ArrayList<>();
    ataquesIds.add(ataque.getId());
    Collection<Long> tiposIds = new ArrayList<>();
    tiposIds.add(tipo.getId());

    PokemonRequestDTO pokemonRequestDTO = new PokemonRequestDTO(
      nomeAlterado,
      pokemon.getAltura(),
      pokemon.getPeso(),
      pokemon.getGenero(),
      pokemon.getNivel(),
      pokemon.getNumeroPokedex(),
      pokemon.getFelicidade(),
      tiposIds,
      ataquesIds
    );

    mvc
      .perform(
        put("/api/v1/pokemons/" + pokemon.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(JsonUtil.toJson(pokemonRequestDTO))
      )
      .andExpect(status().isOk());

    Pokemon pokemonsEncontrados = pokemonRepository
      .findById(pokemon.getId())
      .get();

    assertThat(pokemonsEncontrados.getNome()).isEqualTo(nomeAlterado);
  }
}
