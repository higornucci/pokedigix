package br.com.digix.pokedigix.pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import static org.assertj.core.api.Assertions.assertThat;
<<<<<<< HEAD
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.Collection;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
=======
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
>>>>>>> dev

import br.com.digix.pokedigix.PokedigixApplication;
import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.ataque.AtaqueBuilder;
import br.com.digix.pokedigix.ataque.AtaqueRepository;
import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRepository;
import br.com.digix.pokedigix.utils.JsonUtil;
<<<<<<< HEAD

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PokedigixApplication.class)
=======
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

<<<<<<< HEAD
@SpringBootTest(
  webEnvironment = WebEnvironment.RANDOM_PORT,
  classes = PokedigixApplication.class
)
>>>>>>> dev
=======
import br.com.digix.pokedigix.PokedigixApplication;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PokedigixApplication.class)
>>>>>>> dev
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class PokemonControllerTest {
    @Autowired
    private MockMvc mvc;

<<<<<<< HEAD
<<<<<<< HEAD
    @Autowired
    private MockMvc mvc;

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private AtaqueRepository ataqueRepository;

    @BeforeEach
    @AfterEach
    void resetDb() {
        pokemonRepository.deleteAll();
        ataqueRepository.deleteAll();
        tipoRepository.deleteAll();
    }

    @Test
    void deve_buscar_um_pokemon_pelo_id_do_tipo() throws Exception {
        // Arrange
        String nome = "Ghost";
        Tipo tipoEsperado = new Tipo(nome);
        tipoRepository.save(tipoEsperado);
        Ataque ataque = new AtaqueBuilder().comTipo(tipoEsperado).construir();
        ataqueRepository.save(ataque);
        Pokemon pokemon = new PokemonBuilder().comTipo(tipoEsperado).comAtaque(ataque).construir();
        pokemonRepository.save(pokemon);
        Collection<Tipo> tipos = pokemon.getTipos();
        long idEsperado = 1;

        for (Tipo tipo : tipos) {
            idEsperado = tipo.getId();
        }

        // Action
        MvcResult mvcResult = mvc.perform(get("/api/v1/pokemons/tipo/" + idEsperado)).andReturn();

        // Assert
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);

        PokemonResponseDTO[] pokemonRetornados = JsonUtil.mapFromJson(mvcResult.getResponse().getContentAsString(),
                PokemonResponseDTO[].class);

        assertThat(pokemonRetornados).extracting(PokemonResponseDTO::getNome).containsOnly(pokemon.getNome());
    }

    @Test
    void deve_buscar_um_pokemon_pelo_nome() throws Exception {
        // Arrange
        String nome = "Ghost";
        Tipo tipoEsperado = new Tipo(nome);
        tipoRepository.save(tipoEsperado);
        Ataque ataque = new AtaqueBuilder().comTipo(tipoEsperado).construir();
        ataqueRepository.save(ataque);
        Pokemon pokemon = new PokemonBuilder().comNome(nome).comTipo(tipoEsperado).comAtaque(ataque).construir();
        pokemonRepository.save(pokemon);

        // Action
        MvcResult mvcResult = mvc.perform(get("/api/v1/pokemons")).andReturn();

        // Assert
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);

        PokemonResponseDTO[] pokemonRetornados = JsonUtil.mapFromJson(mvcResult.getResponse().getContentAsString(),
                PokemonResponseDTO[].class);

        assertThat(pokemonRetornados).extracting(PokemonResponseDTO::getNome).containsOnly(nome);

    }
=======
  @Autowired
  private MockMvc mvc;
=======
    @Autowired
    private PokemonRepository pokemonRepository;
>>>>>>> dev

    @Autowired
    private TipoRepository tipoRepository;
  
    @Autowired
    private AtaqueRepository ataqueRepository;

    @AfterEach
    @BeforeEach
    public void resetDb() {
        pokemonRepository.deleteAll();
        ataqueRepository.deleteAll();
        tipoRepository.deleteAll();
    }

    @Test
    public void deve_excluir_um_pokemon() throws Exception {
        // Teste do código Do Enzão
        int quantidadeEsperada = 0;

        Pokemon pokemon = new PokemonBuilder().construir();
        pokemonRepository.save(pokemon);

        String url = "/api/v1/pokemons/" + pokemon.getId();
        MvcResult resultado = mvc.perform(delete(url)).andReturn();

        Iterable<Pokemon> pokemonsEncontrados = pokemonRepository.findAll();
        long quantidadeEncontrada = pokemonsEncontrados.spliterator().getExactSizeIfKnown();

        assertEquals(quantidadeEsperada, quantidadeEncontrada);
    }
  @Test
  void deve_criar_um_pokemon() throws Exception {
    String tipao = "Agua";

    Tipo tipo = new Tipo(tipao);
    Ataque ataque = new AtaqueBuilder().comTipo(tipo).construir();
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
  void deve_atualizar_um_Pokemon() throws Exception {
    //Arrange
    String nomeAlterado = "Eeve";
    String tipao = "Fogo";

    Tipo tipo = new Tipo(tipao);
    Ataque ataque = new AtaqueBuilder().comTipo(tipo).construir();
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
<<<<<<< HEAD
>>>>>>> dev
}
=======
}
>>>>>>> dev
