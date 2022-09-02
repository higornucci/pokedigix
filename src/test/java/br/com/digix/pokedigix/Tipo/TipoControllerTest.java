package br.com.digix.pokedigix.tipo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.digix.pokedigix.PokedigixApplication;
import br.com.digix.pokedigix.utils.JsonUtil;
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

@SpringBootTest(
  webEnvironment = WebEnvironment.RANDOM_PORT,
  classes = PokedigixApplication.class
)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class TipoControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private TipoRepository tipoRepository;

  @BeforeEach
  public void resetDb() {
    tipoRepository.deleteAll();
  }

  @Test
  public void deve_adicionar_um_tipo() throws Exception {
    String nomeEsperado = "Fire";
    int quantidadeEsperada = 1;
    TipoRequestDTO tipoRequestDTO = new TipoRequestDTO(nomeEsperado);

    // Action
    mvc
      .perform(
        post("/api/v1/tipos")
          .contentType(MediaType.APPLICATION_JSON)
          .content(JsonUtil.toJson(tipoRequestDTO))
      )
      .andExpect(status().isCreated());

    // Asserts
    Iterable<Tipo> tiposEncontrados = tipoRepository.findAll();
    long quantidadeEncontrada = tiposEncontrados
      .spliterator()
      .getExactSizeIfKnown();

    assertThat(quantidadeEncontrada).isEqualTo(quantidadeEsperada);
    assertThat(tiposEncontrados)
      .extracting(Tipo::getNome)
      .containsOnly(nomeEsperado);
  }

  @Test
  public void deve_buscar_um_tipo_pelo_id() throws Exception {
    // Arrange
    String nome = "Fire";
    Tipo tipo = new Tipo(nome);
    tipoRepository.save(tipo);

    // Action
    MvcResult mvcResult = mvc
      .perform(get("/api/v1/tipos/" + tipo.getId()))
      .andReturn();

    // Assert
    int status = mvcResult.getResponse().getStatus();
    assertEquals(HttpStatus.OK.value(), status);

    String content = mvcResult.getResponse().getContentAsString();
    TipoResponseDTO tipoDTO = JsonUtil.mapFromJson(
      content,
      TipoResponseDTO.class
    );

    assertThat(tipoDTO.getId()).isEqualTo(tipo.getId());
  }

  @Test
  public void deve_buscar_todos_os_tipos_cadastrados() throws Exception {
    // Arrange
    String eletrico = "eletrico";
    String agua = "agua";
    String fantasma = "fantasma";
    tipoRepository.save(new Tipo(eletrico));
    tipoRepository.save(new Tipo(agua));
    tipoRepository.save(new Tipo(fantasma));

    // Action
    MvcResult resultado = mvc.perform(get("/api/v1/tipos")).andReturn();

    // Assert
    TipoResponseDTO[] tiposRetornados = JsonUtil.mapFromJson(
      resultado.getResponse().getContentAsString(),
      TipoResponseDTO[].class
    );

    assertThat(HttpStatus.OK.value())
      .isEqualTo(resultado.getResponse().getStatus());

    assertThat(tiposRetornados).extracting("nome").contains(eletrico);
  }

  @Test
  public void deve_deletar_um_tipo_pelo_id() throws Exception {
    // Arrange
    int quantidadeEsperada = 2;
    String eletrico = "Eletrico";
    String agua = "Agua";
    String fantasma = "Fantasma";
    Tipo tipoEletrico = new Tipo(eletrico);
    tipoRepository.save(tipoEletrico);
    tipoRepository.save(new Tipo(agua));
    tipoRepository.save(new Tipo(fantasma));

    // Action
    String url = "/api/v1/tipos/" + tipoEletrico.getId();
    MvcResult resultado = mvc.perform(delete(url)).andReturn();

    // Assert
    Iterable<Tipo> tiposEncontrados = tipoRepository.findAll();
    long quantidadeEncontrada = tiposEncontrados
      .spliterator()
      .getExactSizeIfKnown();

    assertThat(quantidadeEncontrada).isEqualTo(quantidadeEsperada);

    assertThat(HttpStatus.NO_CONTENT.value())
      .isEqualTo(resultado.getResponse().getStatus());
  }

  @Test
  public void deve_atualizar_um_tipo() throws Exception {
    //Arrange
    String nomeAlterado = "Charmander";
    String nome = "Fire";
    Tipo tipo = new Tipo(nome);
    tipoRepository.save(tipo);

    TipoRequestDTO tipoRequestDTO = new TipoRequestDTO(nomeAlterado);

    // Action
    mvc
      .perform(
        put("/api/v1/tipos/" + tipo.getId())
          .contentType(MediaType.APPLICATION_JSON)
          .content(JsonUtil.toJson(tipoRequestDTO))
      )
      .andExpect(status().isOk());

    // Asserts
    Tipo tiposEncontrados = tipoRepository.findById(tipo.getId()).get();

    assertThat(tiposEncontrados.getNome()).isEqualTo(nomeAlterado);
  }
}
