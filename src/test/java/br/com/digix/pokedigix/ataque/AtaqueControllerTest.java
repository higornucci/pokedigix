package br.com.digix.pokedigix.ataque;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.digix.pokedigix.PokedigixApplication;
import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRepository;
import br.com.digix.pokedigix.utils.JsonUtil;
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


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PokedigixApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class AtaqueControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private TipoRepository tipoRepository;

  @Autowired
  private AtaqueRepository ataqueRepository;

  @BeforeEach
  @AfterEach
  public void resetDb() {
    ataqueRepository.deleteAll();
    tipoRepository.deleteAll();
  }

  @Test
  public void deve_adicionar_um_ataque() throws Exception {
    // Arrange
    int quantidadeEsperada = 1;
    String nomeDoTipo = "Eletrico";
    Tipo tipo = new Tipo(nomeDoTipo);
    tipoRepository.save(tipo);
    Long tipoId = tipo.getId();
    int acuraciaEsperada = 10;
    Categoria categoriaEsperada = Categoria.FISICO;
    String descricaoEsperada = "Shock Wave na fuça.";
    int forcaEsperada = 30;
    int pontosDePoderEsperados = 20;
    String nomeDoAtaqueEsperado = "Shock Wave";
    AtaqueRequestDTO ataqueRequestDTO = new AtaqueRequestDTO();
    ataqueRequestDTO.setTipoId(tipoId);
    ataqueRequestDTO.setAcuracia(acuraciaEsperada);
    ataqueRequestDTO.setCategoria(categoriaEsperada);
    ataqueRequestDTO.setDescricao(descricaoEsperada);
    ataqueRequestDTO.setForca(forcaEsperada);
    ataqueRequestDTO.setNome(nomeDoAtaqueEsperado);
    ataqueRequestDTO.setPontosDePoder(pontosDePoderEsperados);

    // Act
    mvc.perform(post("/api/v1/ataques")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.toJson(ataqueRequestDTO)))
        .andExpect(status().isCreated());

    // Asserts

    Iterable<Ataque> ataquesEncontrados = ataqueRepository.findAll();
    long quantidadeEncontrada = ataquesEncontrados.spliterator().getExactSizeIfKnown();
    assertThat(quantidadeEncontrada).isEqualTo(quantidadeEsperada);
    assertThat(ataquesEncontrados).extracting(Ataque::getNome).containsOnly(nomeDoAtaqueEsperado);
  }

}