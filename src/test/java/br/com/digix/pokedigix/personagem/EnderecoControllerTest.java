package br.com.digix.pokedigix.personagem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import br.com.digix.pokedigix.PokedigixApplication;
import br.com.digix.pokedigix.utils.JsonUtil;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PokedigixApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class EnderecoControllerTest {
    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private EnderecoRepository enderecoRepository;
    
    @AfterEach
    public void resetDb() {
        enderecoRepository.deleteAll();
    }
    @Test
    public void deve_excluir_um_endereco_pelo_id() throws Exception {
        // Teste do código Do Enzão
        int quantidadeEsperada = 0;
        
        Endereco endereco = new EnderecoBuilder().construir();
        enderecoRepository.save(endereco);

        String url = "/api/v1/enderecos/" + endereco.getId();
        mvc.perform(delete(url)).andReturn();

        Iterable<Endereco> enderecosEncontrados = enderecoRepository.findAll();
        long quantidadeEncontrada = enderecosEncontrados.spliterator().getExactSizeIfKnown();

        assertEquals(quantidadeEsperada, quantidadeEncontrada);
    }
    @Test
    public void deve_cadastrar_um_novo_endereco() throws Exception {
        int quantidadeEsperada = 1;
        Endereco endereco = new EnderecoBuilder().construir();
        EnderecoRequestDTO enderecoRequestDTO = new EnderecoRequestDTO(endereco.getRegiao(), endereco.getCidade());
        
        mvc.perform(post("/api/v1/enderecos/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.toJson(enderecoRequestDTO)))
        .andExpect(status().isCreated());

        Iterable<Endereco> buscaEndereco = enderecoRepository.findAll();
        long quantidadeEncontrada = buscaEndereco.spliterator().getExactSizeIfKnown();

        assertThat(quantidadeEncontrada).isEqualTo(quantidadeEsperada);
        assertThat(buscaEndereco).extracting(Endereco::getCidade).containsOnly(endereco.getCidade());

    }
  @Test
  void deve_adicionar_um_endereco() throws Exception {
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
