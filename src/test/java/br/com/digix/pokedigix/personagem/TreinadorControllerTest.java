package br.com.digix.pokedigix.personagem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import br.com.digix.pokedigix.PokedigixApplication;
import br.com.digix.pokedigix.pokemon.Pokemon;
import br.com.digix.pokedigix.pokemon.PokemonBuilder;
import br.com.digix.pokedigix.pokemon.PokemonRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PokedigixApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class TreinadorControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private TreinadorRepository treinadorRepository;

    @Autowired
    private PokemonRepository pokemonRepository;
    
    @Autowired
    private EnderecoRepository enderecoRepository;

    @AfterEach
    public void resetDb() {
        treinadorRepository.deleteAll();
    }

    @Test
    public void deve_excluir_um_treinador() throws Exception {
        // Teste do código Do Enzão
        int quantidadeEsperada = 0;

        Pokemon pokemonInicial = new PokemonBuilder().construir();
        pokemonRepository.save(pokemonInicial);

       
        Endereco endereco = new Endereco("Kanto", "Pallet") ;
        enderecoRepository.save(endereco);
        Treinador treinador = new TreinadorBuilder().comPokemonInicial(pokemonInicial).comEndereco(endereco).construir();
        treinadorRepository.save(treinador);
        

        String url = "/api/v1/treinadores/" + treinador.getId();
        MvcResult resultado = mvc.perform(delete(url)).andReturn();

        Iterable<Treinador> treinadoresEncontrados = treinadorRepository.findAll();
        long quantidadeEncontrada = treinadoresEncontrados.spliterator().getExactSizeIfKnown();

        assertEquals(quantidadeEsperada, quantidadeEncontrada);
    }
}