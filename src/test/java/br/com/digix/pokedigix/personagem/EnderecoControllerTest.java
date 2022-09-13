package br.com.digix.pokedigix.personagem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
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
import br.com.digix.pokedigix.utils.JsonUtil;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PokedigixApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
class EnderecoControllerTest {
  @Autowired
  private MockMvc mvc;

  @Autowired
  private EnderecoRepository enderecoRepository;

  @AfterEach
  public void resetDb() {
    enderecoRepository.deleteAll();
  }

  @Test
  void deve_excluir_um_endereco_pelo_id() throws Exception {
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
  void deve_cadastrar_um_novo_endereco() throws Exception {
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
                .content(JsonUtil.toJson(enderecoRequestDTO)))
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

  @Test
  void deve_atualizar_um_endereco() throws Exception {
    String regiao = "Centro Oeste";
    String regiaoAtualizada = "Sul";
    String cidade = "Campo Grande";
    Endereco endereco = new Endereco(regiao, cidade);
    enderecoRepository.save(endereco);
    int quantidadeEsperada = 1;
    EnderecoUpdateDTO tipoRequestDTO = new EnderecoUpdateDTO(regiaoAtualizada, cidade);

    // Action
    String url = "/api/v1/enderecos/" + endereco.getId();
    mvc.perform(put(url).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(tipoRequestDTO)))
        .andExpect(status().isOk());

    // Assert
    Iterable<Endereco> enderecoEnconrados = enderecoRepository.findAll();
    long quantidadeEncontrada = enderecoEnconrados.spliterator().getExactSizeIfKnown();

    assertThat(quantidadeEncontrada)
        .isEqualTo(quantidadeEsperada);

    assertThat(enderecoEnconrados).extracting(Endereco::getRegiao).containsOnly(regiaoAtualizada);
  }

  @Test
  void deve_buscar_pelo_nome_da_cidade() throws Exception {
    String cidade = "Pallet";
    Endereco endereco = new EnderecoBuilder().comCidade(cidade).construir();
    enderecoRepository.save(endereco);
    String novaCidade = "Pallioto";
    Endereco novoEndereco = new EnderecoBuilder().comCidade(novaCidade).construir();
    enderecoRepository.save(novoEndereco);
    String potencialCidade = "Pall";

    // Action
    MvcResult resultado = mvc.perform(get("/api/v1/enderecos/cidade?termo=" + potencialCidade)).andReturn();

    // Assertions
    EnderecoResponseDTO[] enderecosRetornadosDTO = JsonUtil.mapFromJson(resultado.getResponse().getContentAsString(),
        EnderecoResponseDTO[].class);

    assertThat(HttpStatus.OK.value()).isEqualTo(resultado.getResponse().getStatus());
    assertThat(enderecosRetornadosDTO).extracting("cidade").contains(novaCidade);
    assertThat(enderecosRetornadosDTO).hasSize(2);
  }

  @Test
  void deve_buscar_lista_de_cidades_quando_vazio() throws Exception {
    String cidade = "Pallet";
    Endereco endereco = new EnderecoBuilder().comCidade(cidade).construir();
    enderecoRepository.save(endereco);
    String novaCidade = "Pallioto";
    Endereco novoEndereco = new EnderecoBuilder().comCidade(novaCidade).construir();
    enderecoRepository.save(novoEndereco);
    String potencialCidade = "";
    int quantidadeEsperada = 2;

    // Action
    MvcResult resultado = mvc.perform(get("/api/v1/enderecos/cidade?termo=" + potencialCidade)).andReturn();

    // Assertions
    EnderecoResponseDTO[] enderecosRetornadosDTO = JsonUtil.mapFromJson(resultado.getResponse().getContentAsString(),
        EnderecoResponseDTO[].class);

    assertThat(HttpStatus.OK.value()).isEqualTo(resultado.getResponse().getStatus());
    assertThat(enderecosRetornadosDTO).hasSize(quantidadeEsperada);
    assertThat(enderecosRetornadosDTO).extracting("cidade").contains(cidade);
  }

  @Test
  void deve_buscar_lista_de_cidades_quando_null() throws Exception {
    String cidade = "Pallet";
    Endereco endereco = new EnderecoBuilder().comCidade(cidade).construir();
    enderecoRepository.save(endereco);
    String novaCidade = "Pallioto";
    Endereco novoEndereco = new EnderecoBuilder().comCidade(novaCidade).construir();
    enderecoRepository.save(novoEndereco);
    int quantidadeEsperada = 2;

    // Action
    MvcResult resultado = mvc.perform(get("/api/v1/enderecos/cidade?")).andReturn();

    // Assertions
    EnderecoResponseDTO[] enderecosRetornadosDTO = JsonUtil.mapFromJson(resultado.getResponse().getContentAsString(),
        EnderecoResponseDTO[].class);

    assertThat(HttpStatus.OK.value()).isEqualTo(resultado.getResponse().getStatus());
    assertThat(enderecosRetornadosDTO).hasSize(quantidadeEsperada);
    assertThat(enderecosRetornadosDTO).extracting("cidade").contains(cidade);

  }

  @Test
  void deve_buscar_pelo_nome_da_regiao() throws Exception {
    String regiao = "Kanto";
    Endereco endereco = new EnderecoBuilder().comRegiao(regiao).construir();
    enderecoRepository.save(endereco);
    String novaRegiao = "Kannon";
    Endereco novoEndereco = new EnderecoBuilder().comRegiao(novaRegiao).construir();
    enderecoRepository.save(novoEndereco);
    String potencialRegiao = "Kan";
    int quantidadeEsperada = 2;

    // Action
    MvcResult resultado = mvc.perform(get("/api/v1/enderecos/regiao?termo=" + potencialRegiao)).andReturn();

    // Assertions
    EnderecoResponseDTO[] enderecosRetornadosDTO = JsonUtil.mapFromJson(resultado.getResponse().getContentAsString(),
        EnderecoResponseDTO[].class);

    assertThat(HttpStatus.OK.value()).isEqualTo(resultado.getResponse().getStatus());
    assertThat(enderecosRetornadosDTO).extracting("regiao").containsAnyOf(regiao);
    assertThat(enderecosRetornadosDTO).hasSize(quantidadeEsperada);
  }

  @Test
  void deve_buscar_lista_de_regiao_quando_vazio() throws Exception {
    String regiao = "Kanto";
    Endereco endereco = new EnderecoBuilder().comRegiao(regiao).construir();
    enderecoRepository.save(endereco);
    String novaRegiao = "Hoenn";
    Endereco novoEndereco = new EnderecoBuilder().comRegiao(novaRegiao).construir();
    enderecoRepository.save(novoEndereco);
    String potencialRegiao = "";
    int quantidadeEsperada = 2;

    // Action
    MvcResult resultado = mvc.perform(get("/api/v1/enderecos/regiao?termo=" + potencialRegiao)).andReturn();

    // Assertions
    EnderecoResponseDTO[] enderecosRetornadosDTO = JsonUtil.mapFromJson(resultado.getResponse().getContentAsString(),
        EnderecoResponseDTO[].class);

    assertThat(HttpStatus.OK.value()).isEqualTo(resultado.getResponse().getStatus());
    assertThat(enderecosRetornadosDTO).extracting("regiao").contains(regiao);
    assertThat(enderecosRetornadosDTO).hasSize(quantidadeEsperada);
  }

  @Test
  void deve_buscar_lista_de_regiao_quando_nulo() throws Exception {
    String regiao = "Kanto";
    Endereco endereco = new EnderecoBuilder().comRegiao(regiao).construir();
    enderecoRepository.save(endereco);
    String novaRegiao = "Hoenn";
    Endereco novoEndereco = new EnderecoBuilder().comRegiao(novaRegiao).construir();
    enderecoRepository.save(novoEndereco);
    int quantidadeEsperada = 2;

    // Action
    MvcResult resultado = mvc.perform(get("/api/v1/enderecos/regiao?")).andReturn();

    // Assertions
    EnderecoResponseDTO[] enderecosRetornadosDTO = JsonUtil.mapFromJson(resultado.getResponse().getContentAsString(),
        EnderecoResponseDTO[].class);

    assertThat(HttpStatus.OK.value()).isEqualTo(resultado.getResponse().getStatus());
    assertThat(enderecosRetornadosDTO).extracting("regiao").contains(regiao);
    assertThat(enderecosRetornadosDTO).hasSize(quantidadeEsperada);
  }
}
