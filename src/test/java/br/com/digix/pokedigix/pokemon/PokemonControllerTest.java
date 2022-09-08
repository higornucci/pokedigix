package br.com.digix.pokedigix.pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import br.com.digix.pokedigix.PokedigixApplication;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PokedigixApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class PokemonControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private PokemonRepository pokemonRepository;

    @BeforeEach
    public void resetDb() {
        pokemonRepository.deleteAll();
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
}
 