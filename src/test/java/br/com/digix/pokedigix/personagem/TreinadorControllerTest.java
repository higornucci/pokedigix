package br.com.digix.pokedigix.personagem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.ArrayList;

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
	private PokemonRepository pokemonRepository;

	@Autowired
	private AtaqueRepository ataqueRepository;

	@Autowired
	private TipoRepository tipoRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private TreinadorRepository treinadorRepository;

	@Autowired
	private MockMvc mvc;

	@BeforeEach
	@AfterEach
	public void resetDb() {
		treinadorRepository.deleteAll();
		enderecoRepository.deleteAll();
		pokemonRepository.deleteAll();
		ataqueRepository.deleteAll();
		tipoRepository.deleteAll();
	}

	@Test
	public void deve_atualizar_o_Treinador() throws Exception {
		String nome = "kaioken";
		String regiao = "Nasser";
		String cidade = "cidade";
		int nivel = 100;
		int dinheiro = 999;
		Tipo tipo = new Tipo("eletrico");
		tipoRepository.save(tipo);
		Ataque ataque = new AtaqueBuilder().comTipo(tipo).construir();
		ataqueRepository.save(ataque);
		Pokemon pikachu = new PokemonBuilder().comNome(nome).comTipo(tipo).comAtaque(ataque).construir();
		pokemonRepository.save(pikachu);
		Endereco endereco = new Endereco(regiao, cidade);
		enderecoRepository.save(endereco);
		Treinador treinador = new Treinador(nome, endereco, pikachu);
		treinadorRepository.save(treinador);

		String treinadorNovo = "Kaio";
		TreinadorUpdateDTO TreinadorRequestDTO = new TreinadorUpdateDTO(
				treinadorNovo, endereco.getId(), nivel, dinheiro, new ArrayList<>());
		String url = "/api/v1/treinadores/" + treinador.getId();

		MvcResult resultado = mvc
				.perform(put(url)
						.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
						.content(JsonUtil.toJson(TreinadorRequestDTO)))
				.andReturn();

		int status = resultado.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);

		Iterable<Treinador> treinadoresEncontrados = treinadorRepository.findAll();
		assertThat(treinadoresEncontrados).extracting(Treinador::getNome)
				.containsOnly(treinadorNovo);
	}

}
