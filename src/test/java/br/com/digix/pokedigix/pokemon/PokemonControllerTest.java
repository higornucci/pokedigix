package br.com.digix.pokedigix.pokemon;

import static org.assertj.core.api.Assertions.assertThat;
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

import br.com.digix.pokedigix.PokedigixApplication;
import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.ataque.AtaqueBuilder;
import br.com.digix.pokedigix.ataque.AtaqueRepository;
import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRepository;
import br.com.digix.pokedigix.utils.JsonUtil;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PokedigixApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class PokemonControllerTest {

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
    public void resetDb() {
        pokemonRepository.deleteAll();
        ataqueRepository.deleteAll();
        tipoRepository.deleteAll();
    }

    @Test
    public void deve_buscar_um_pokemon_pelo_id_do_tipo() throws Exception {
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
    public void deve_buscar_um_pokemon_pelo_nome() throws Exception {
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
}
