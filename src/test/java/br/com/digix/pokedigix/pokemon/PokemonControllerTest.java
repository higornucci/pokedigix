package br.com.digix.pokedigix.pokemon;

import static org.assertj.core.api.Assertions.assertThat;
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
	private PokemonRepository pokemonRepository;

	@Autowired
	private AtaqueRepository ataqueRepository;

	@Autowired
	private TipoRepository tipoRepository;

	@BeforeEach
	@AfterEach
	public void resetDb() {
		pokemonRepository.deleteAll();
		ataqueRepository.deleteAll();
		tipoRepository.deleteAll();
	}

	@Test
	public void deve_buscar_todos_os_pokemons() throws Exception {
		int quantidadeEsperada = 3;
		String nome = "Pikachu";
		Tipo tipo = new Tipo("eletrico");
		tipoRepository.save(tipo);
		Ataque ataque = new AtaqueBuilder().comTipo(tipo).construir();
		ataqueRepository.save(ataque);
		Pokemon pikachu = new PokemonBuilder().comNome(nome).comTipo(tipo).comAtaque(ataque).construir();
		pokemonRepository.save(pikachu);
		Pokemon mew = new PokemonBuilder().comTipo(tipo).comAtaque(ataque).construir();
		pokemonRepository.save(mew);
		Pokemon mewTwo = new PokemonBuilder().comTipo(tipo).comAtaque(ataque).construir();
		pokemonRepository.save(mewTwo);

		MvcResult resultado = mvc.perform(get("/api/v1/pokemons/")).andReturn();

		PokemonResponseDTO[] pokemonsRetornados = JsonUtil.mapFromJson(resultado.getResponse().getContentAsString(),
				PokemonResponseDTO[].class);

		assertThat(pokemonsRetornados.length)
				.isEqualTo(quantidadeEsperada);

		assertThat(HttpStatus.OK.value())
				.isEqualTo(resultado.getResponse().getStatus());

		assertThat(pokemonsRetornados)
				.extracting(PokemonResponseDTO::getNome)
				.contains(nome);
	}
}
