package br.com.digix.pokedigix.personagem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import br.com.digix.pokedigix.PokedigixApplication;
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

@SpringBootTest(
  webEnvironment = WebEnvironment.RANDOM_PORT,
  classes = PokedigixApplication.class
)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class EnderecoControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private EnderecoRepository enderecoRepository;

  @AfterEach
  public void resetDb1() {
    enderecoRepository.deleteAll();
  }

  @BeforeEach
  public void resetDb() {
    enderecoRepository.deleteAll();
  }

  @Test
  public void deve_adicionar_um_endereco() throws IOException, Exception {
    int quantidadeEsperada = 1;
    String cidadeEsperada = "Pallet";
    String regiaoEsperada = "Kanto";

    EnderecoRequestDTO enderecoRequestDTO = new EnderecoRequestDTO();
    enderecoRequestDTO.setCidade(cidadeEsperada);
    enderecoRequestDTO.setRegiao(regiaoEsperada);

    mvc
      .perform(
        post("/api/v1/enderecos/")
          .contentType(MediaType.APPLICATION_JSON)
          .content(JsonUtil.toJson(enderecoRequestDTO))
      )
      .andExpect(status().isCreated());

    Iterable<Endereco> enderecosEncontrados = enderecoRepository.findAll();
    long quantidadeEncontrada = enderecosEncontrados
      .spliterator()
      .getExactSizeIfKnown();

    assertThat(quantidadeEncontrada).isEqualTo(quantidadeEsperada);
    assertThat(enderecosEncontrados)
      .extracting(Endereco::getCidade)
      .containsOnly(cidadeEsperada);
    assertThat(enderecosEncontrados)
      .extracting(Endereco::getRegiao)
      .containsOnly(regiaoEsperada);
  }
}
