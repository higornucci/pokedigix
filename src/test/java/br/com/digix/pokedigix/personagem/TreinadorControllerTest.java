package br.com.digix.pokedigix.personagem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.http.MediaType;
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
    private EnderecoRepository enderecoRepository;

    @Autowired
    private AtaqueRepository ataqueRepository;

    @Autowired
    private TipoRepository tipoRepository;

    @AfterEach
    @BeforeEach
    public void resetDb() {
        pokemonRepository.deleteAll();
        treinadorRepository.deleteAll();
        enderecoRepository.deleteAll();
        ataqueRepository.deleteAll();
        tipoRepository.deleteAll();
    }

    @Test
    public void deve_excluir_um_treinador() throws Exception {
        // Teste do código Do Enzão
        int quantidadeEsperada = 0;

        Pokemon pokemonInicial = new PokemonBuilder().construir();
        pokemonRepository.save(pokemonInicial);

        Endereco endereco = new Endereco("Kanto", "Pallet");
        enderecoRepository.save(endereco);
        Treinador treinador = new TreinadorBuilder().comPokemonInicial(pokemonInicial).comEndereco(endereco)
                .construir();
        treinadorRepository.save(treinador);

        String url = "/api/v1/treinadores/" + treinador.getId();
        mvc.perform(delete(url)).andReturn();

        Iterable<Treinador> treinadoresEncontrados = treinadorRepository.findAll();
        long quantidadeEncontrada = treinadoresEncontrados.spliterator().getExactSizeIfKnown();

        assertEquals(quantidadeEsperada, quantidadeEncontrada);
    }

    @Test
    public void deve_adicionar_um_treinador() throws Exception {
        // Arrange
        int quantidadeEsperada = 1;
        Endereco endereco = new EnderecoBuilder().construir();
        enderecoRepository.save(endereco);
        Long enderecoId = endereco.getId();
        Pokemon pokemonInicialEsperado = new PokemonBuilder().construir();
        pokemonRepository.save(pokemonInicialEsperado);
        Long pokemonId = pokemonInicialEsperado.getId();
        String nomeTreinadorEsperado = "Ash";
        TreinadorRequestDTO treinadorRequestDTO = new TreinadorRequestDTO();
        treinadorRequestDTO.setIdEndereco(enderecoId);
        treinadorRequestDTO.setIdPrimeiroPokemon(pokemonId);
        treinadorRequestDTO.setNome(nomeTreinadorEsperado);
        // Act
        mvc.perform(post("/api/v1/treinadores")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(treinadorRequestDTO)))
                .andExpect(status().isCreated());

        // Assert
        Iterable<Treinador> treinadoresEncontrados = treinadorRepository.findAll();
        long quantidadeEncontrada = treinadoresEncontrados.spliterator().getExactSizeIfKnown();
        assertThat(quantidadeEncontrada).isEqualTo(quantidadeEsperada);
        assertThat(treinadoresEncontrados).extracting(Treinador::getNome).containsOnly(nomeTreinadorEsperado);

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

        assertThat(pokemonsRetornadDtos).hasSize(quantidadeEsperada);
        assertThat(pokemonsRetornadDtos).extracting("nome").contains(pokemon.getNome());
    }
}
