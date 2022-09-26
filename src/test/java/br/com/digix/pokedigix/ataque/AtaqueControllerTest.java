package br.com.digix.pokedigix.ataque;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import br.com.digix.pokedigix.utils.JsonUtil;

import br.com.digix.pokedigix.tipo.TipoRepository;


import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import br.com.digix.pokedigix.PokedigixApplication;
import br.com.digix.pokedigix.tipo.Tipo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PokedigixApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class AtaqueControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private AtaqueRepository ataqueRepository;

	@Autowired
	private TipoRepository tipoRepository;

	@BeforeEach
	@AfterEach
	public void resetDb() {
		ataqueRepository.deleteAll();
		tipoRepository.deleteAll();
	}

	@Test
	void deve_adicionar_um_ataque() throws Exception {
		int quantidadeEsperada = 1;
		Tipo tipoEsperado = new Tipo("Normal");
		tipoRepository.save(tipoEsperado);
		long idTipo = tipoEsperado.getId();
		int forca = 60;
		int acuracia = 80;
		int pontosDePoder = 74;
		Categoria categoria = Categoria.FISICO;
		String nome = "Ataque Rapido";
		String descricao = "ataque na pança";

		AtaqueRequestDTO ataqueRequestDTO = new AtaqueRequestDTO();

		ataqueRequestDTO.setTipoId(idTipo);
		ataqueRequestDTO.setForca(forca);
		ataqueRequestDTO.setAcuracia(acuracia);
		ataqueRequestDTO.setPontosDePoder(pontosDePoder);
		ataqueRequestDTO.setCategoria(categoria);
		ataqueRequestDTO.setNome(nome);
		ataqueRequestDTO.setDescricao(descricao);

		mvc.perform(post("/api/v1/ataques/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(ataqueRequestDTO)))
				.andExpect(status().isCreated());

		Iterable<Ataque> ataquesEncontrados = ataqueRepository.findAll();
		long quantidadeEncontrada = ataquesEncontrados.spliterator().getExactSizeIfKnown();

		assertThat(quantidadeEncontrada).isEqualTo(quantidadeEsperada);
		assertThat(ataquesEncontrados).extracting(Ataque::getNome).contains(nome);

	}

	@Test
	void deve_adicionar_um_ataque_de_efeito() throws Exception {
		int quantidadeEsperada = 1;
		Tipo tipoEsperado = new Tipo("Normal");
		tipoRepository.save(tipoEsperado);
		long idTipo = tipoEsperado.getId();
		int acuracia = 80;
		int pontosDePoder = 15;
		Categoria categoria = Categoria.EFEITO;
		String nome = "Po do Sono";
		String descricao = "Um poh sonifero que faz o oponente dormir por 3 turnos";

		AtaqueRequestDTO ataqueRequestDTO = new AtaqueRequestDTO();

		ataqueRequestDTO.setTipoId(idTipo);
		ataqueRequestDTO.setAcuracia(acuracia);
		ataqueRequestDTO.setPontosDePoder(pontosDePoder);
		ataqueRequestDTO.setCategoria(categoria);
		ataqueRequestDTO.setNome(nome);
		ataqueRequestDTO.setDescricao(descricao);

		mvc.perform(post("/api/v1/ataques/")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJson(ataqueRequestDTO)))
				.andExpect(status().isCreated());

		Iterable<Ataque> ataquesEncontrados = ataqueRepository.findAll();
		long quantidadeEncontrada = ataquesEncontrados.spliterator().getExactSizeIfKnown();

		assertThat(quantidadeEncontrada).isEqualTo(quantidadeEsperada);
		assertThat(ataquesEncontrados).extracting(Ataque::getNome).contains(nome);

	}

	@Test
	void deve_excluir_um_ataque_pelo_id() throws Exception {
		// Teste do código Do Enzão
		int quantidadeEsperada = 0;

		Tipo tipo = new Tipo("Eletrico");

		Ataque ataque = new AtaqueBuilder().comTipo(tipo).construir();
		ataqueRepository.save(ataque);

		String url = "/api/v1/ataques/" + ataque.getId();
		mvc.perform(delete(url)).andReturn();

		Iterable<Ataque> ataquesEncontrados = ataqueRepository.findAll();
		long quantidadeEncontrada = ataquesEncontrados.spliterator().getExactSizeIfKnown();

		assertEquals(quantidadeEsperada, quantidadeEncontrada);
	}

	@Test
	void deve_atualizar_o_Ataque() throws Exception {
		Tipo tipo = new Tipo("eletrico");
		String nome = "eletro pau";
		int forca = 90;
		int acuracia = 100;
		int pontosDePoder = 80;
		Categoria categoria = Categoria.ESPECIAL;
		String descricao = "Tomale choque!!!";
		Ataque ataque = new AtaqueBuilder().comTipo(tipo).comNome(nome).construir();
		ataqueRepository.save(ataque);

		String novoAtaque = "Choque do trovão";
		AtaqueRequestDTO ataqueRequestDTO = new AtaqueRequestDTO(
				forca, acuracia, pontosDePoder, tipo.getId(), categoria, novoAtaque, descricao);

		String url = "/api/v1/ataques/" + ataque.getId();

		MvcResult resultado = mvc
				.perform(put(url)
						.contentType(org.springframework.http.MediaType.APPLICATION_JSON)
						.content(JsonUtil.toJson(ataqueRequestDTO)))
				.andReturn();

		int status = resultado.getResponse().getStatus();
		assertEquals(HttpStatus.OK.value(), status);

                Iterable<Ataque> ataquesEncontrados = ataqueRepository.findAll();
                assertThat(ataquesEncontrados).extracting(Ataque::getNome)
                                .containsOnly(novoAtaque);
        }

        @Test
        void deve_buscar_um_ataque_pelo_id() throws Exception {
                Ataque ataque = new AtaqueBuilder().construir();
                ataqueRepository.save(ataque);

                // Action
                MvcResult mvcResult = mvc.perform(get("/api/v1/ataques/" + ataque.getId())).andReturn();

                // Assert
                int status = mvcResult.getResponse().getStatus();
                assertEquals(HttpStatus.OK.value(), status);

                String content = mvcResult.getResponse().getContentAsString();
                AtaqueResponseDTO ataqueDTO = JsonUtil.mapFromJson(content, AtaqueResponseDTO.class);

                assertThat(ataqueDTO.getId()).isEqualTo(ataque.getId());
        }

		@Test
        void deve_buscar_pelo_nome_parcial_ou_completo() throws Exception{
                //arange
                String tipo = "Fairy";
                String nome = "Beijo Dranante";
                Tipo tipoEsperado = new Tipo(tipo);
                Ataque ataque = new AtaqueBuilder().comNome(nome).comTipo(tipoEsperado).construir();
                ataqueRepository.save(ataque);

                MvcResult mvcResult = mvc.perform(get("/api/v1/ataques")).andReturn();

                int status = mvcResult.getResponse().getStatus();
                assertEquals(HttpStatus.OK.value(), status);

                AtaqueResponseDTO[] ataquesRetornados = JsonUtil.mapFromJson(
                        mvcResult.getResponse().getContentAsString(),
                        AtaqueResponseDTO[].class);

                assertThat(ataquesRetornados).extracting(AtaqueResponseDTO::getNome).containsOnly(nome);
               
                
        }
}