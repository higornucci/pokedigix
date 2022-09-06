package br.com.digix.pokedigix.personagem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


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
    private TipoRepository tipoRepository;
    @Autowired
    private AtaqueRepository ataqueRepository;

    @AfterEach
    @BeforeEach
    public void resetDb() {
        pokemonRepository.deleteAll();
        ataqueRepository.deleteAll();
        treinadorRepository.deleteAll();
        enderecoRepository.deleteAll();
        tipoRepository.deleteAll();
    }

    @Test
    public void deve_buscar_um_treinador_pelo_id() throws Exception {
        // Arrange
        String nome = "Flavio";
        Endereco endereco = new Endereco("centro-oeste", "Campo-Grande");
        enderecoRepository.save(endereco);
        Tipo tipo = new Tipo("Agua");
        tipoRepository.save(tipo);
        Ataque ataque = new AtaqueBuilder().comTipo(tipo).construir();
        ataqueRepository.save(ataque);
        Pokemon primeriPokemon = new PokemonBuilder().comAtaque(ataque).comTipo(tipo).construir();
        pokemonRepository.save(primeriPokemon);
        Treinador treinador = new TreinadorBuilder().comPokemonInicial(primeriPokemon).comEndereco(endereco).construir();
        treinadorRepository.save(treinador);

        // Action
        MvcResult mvcResult = mvc.perform(get("/api/v1/treinadores/" + treinador.getId())).andReturn();

        // Assert
        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK.value(), status);

        String content = mvcResult.getResponse().getContentAsString();
        TreinadorResponseDTO treinadorDTO = JsonUtil.mapFromJson(content, TreinadorResponseDTO.class);

        assertThat(treinadorDTO.getId()).isEqualTo(treinador.getId());
    }

}
