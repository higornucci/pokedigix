package br.com.digix.pokedigix.ataque;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.digix.pokedigix.PokedigixApplication;
import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRepository;
import br.com.digix.pokedigix.utils.JsonUtil;

@SpringBootTest(
  webEnvironment = WebEnvironment.RANDOM_PORT, classes = PokedigixApplication.class
)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class AtaqueControllerTest {

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
  public void deve_adicionar_um_ataque() throws Exception {
    int quantidadeEsperada = 1;
    String nomeDoTipo = "Watter";
    Tipo tipo = new Tipo(nomeDoTipo);
    tipoRepository.save(tipo);
    String nomeDoAtaque = "Aqua-Jet";

    AtaqueRequestDTO ataqueRequestDTO = new AtaqueRequestDTO();
    ataqueRequestDTO.setTipoId(tipo.getId());
    ataqueRequestDTO.setAcuracia(10);
    ataqueRequestDTO.setCategoria(Categoria.FISICO);
    ataqueRequestDTO.setDescricao("Cospe agua no outro");
    ataqueRequestDTO.setForca(30);
    ataqueRequestDTO.setNome(nomeDoAtaque);
    ataqueRequestDTO.setPontosDePoder(20);

    // Action
    mvc
      .perform(
        post("/api/v1/ataques/")
          .contentType(MediaType.APPLICATION_JSON)
          .content(JsonUtil.toJson(ataqueRequestDTO)))
      .andExpect(status().isCreated());

    // Asserts
    Iterable<Ataque> ataquesEncontrados = ataqueRepository.findAll();
    long quantidadeEncontrada = ataquesEncontrados
      .spliterator()
      .getExactSizeIfKnown();

    assertThat(quantidadeEncontrada).isEqualTo(quantidadeEsperada);
    assertThat(ataquesEncontrados).extracting(Ataque::getNome).containsOnly(nomeDoAtaque);
  }
}
