package br.com.digix.pokedigix.personagem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import javax.transaction.Transactional;

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
import br.com.digix.pokedigix.pokemon.Pokemon;
import br.com.digix.pokedigix.pokemon.PokemonBuilder;
import br.com.digix.pokedigix.pokemon.PokemonRepository;
import br.com.digix.pokedigix.pokemon.PokemonResponseDTO;
import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRepository;
import br.com.digix.pokedigix.utils.JsonUtil;

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
    private TipoRepository tipoRepository;

    @Autowired
    private AtaqueRepository ataqueRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @BeforeEach
    @AfterEach
    public void resetDb() {
        pokemonRepository.deleteAll();
        treinadorRepository.deleteAll();
        ataqueRepository.deleteAll();
        tipoRepository.deleteAll();
        enderecoRepository.deleteAll();
    }

    @Test
    @Transactional
    public void deve_buscar_os_pokemons_do_treinador() throws Exception {
        // Arrange
        int quantidadeEsperada = 1;
        Tipo tipo = new Tipo("Eletrico");
        Ataque ataque = new AtaqueBuilder().comTipo(tipo).construir();
        Pokemon pokemon = new PokemonBuilder().comAtaque(ataque).comTipo(tipo).construir();
        Endereco endereco = new Endereco("Kanto", "Pallet");
        enderecoRepository.save(endereco);
        Treinador treinador = new TreinadorBuilder().comPokemonInicial(pokemon).comEndereco(endereco).construir();
        treinadorRepository.save(treinador);

        // Action
        MvcResult resultado = mvc.perform(get("/api/v1/treinadores/" + treinador.getId() + "/pokemons")).andReturn();

        // Assert
        assertThat(HttpStatus.OK.value()).isEqualTo(resultado.getResponse().getStatus());

        PokemonResponseDTO[] pokemonsRetornadDtos = JsonUtil.mapFromJson(resultado.getResponse().getContentAsString(),
                PokemonResponseDTO[].class);

        assertThat(pokemonsRetornadDtos.length).isEqualTo(quantidadeEsperada);
        assertThat(pokemonsRetornadDtos).extracting("nome").contains(pokemon.getNome());
    }
}
