package br.com.digix.pokedigix.Tipo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
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
import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRepository;
import br.com.digix.pokedigix.tipo.TipoRequestDTO;
import br.com.digix.pokedigix.tipo.TipoResponseDTO;
import br.com.digix.pokedigix.utils.JsonUtil;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PokedigixApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class TipoControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TipoRepository tipoRepository;

    @AfterEach
    public void resetDb() {
        tipoRepository.deleteAll();
    }

    @Test
    public void deve_adicionar_um_tipo() throws Exception {
        String nomeEsperado = "Fire";
        int quantidadeEsperada = 1;
        TipoRequestDTO tipoRequestDTO = new TipoRequestDTO(nomeEsperado);
        mvc.perform(
                post("/api/v1/tipos").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(tipoRequestDTO)))
                .andExpect(status().isCreated());

        Iterable<Tipo> tiposEncontrados = tipoRepository.findAll();
        long quantidadeEncontrada = tiposEncontrados.spliterator().getExactSizeIfKnown();

        mvc.perform(post("/api/v1/tipos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(tipoRequestDTO)))
                .andExpect(status().isCreated());

        assertThat(quantidadeEncontrada)
                .isEqualTo(quantidadeEsperada);

        assertThat(tiposEncontrados)
                .extracting(Tipo::getNome)
                .containsOnly(nomeEsperado);
    }

    @Test
    public void deve_buscar_um_tipo_pelo_id() throws Exception {

        String nome = "Fire";
        Tipo tipo = new Tipo(nome);
        tipoRepository.save(tipo);

        MvcResult mvcResult = mvc.perform(get("/api/v1/tipos/" + tipo.getId()).accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        int status = mvcResult.getResponse().getStatus();

        assertEquals(HttpStatus.OK.value(), status);

        String content = mvcResult.getResponse().getContentAsString();
        TipoResponseDTO tipoDTO = JsonUtil.mapFromJson(content, TipoResponseDTO.class);

        assertThat(tipoDTO.getId()).isEqualTo(tipo.getId());
    }

    @Test
    public void deve_buscar_todos_os_tipos_cadastrados() throws Exception {

        int quantidadeEsperada = 3;
        String eletrico = "Eletrico";
        String agua = "Agua";
        String fantasma = "Fantasma";
        tipoRepository.save(new Tipo(eletrico));
        tipoRepository.save(new Tipo(agua));
        tipoRepository.save(new Tipo(fantasma));

        MvcResult resultado = mvc.perform(get("/api/v1/tipos")).andReturn();

        TipoResponseDTO[] tiposRetornados = JsonUtil.mapFromJson(resultado.getResponse().getContentAsString(),
                TipoResponseDTO[].class);

        assertThat(tiposRetornados.length)
                .isEqualTo(quantidadeEsperada);

        assertThat(HttpStatus.OK.value())
                .isEqualTo(resultado.getResponse().getStatus());

        assertThat(tiposRetornados)
                .extracting(TipoResponseDTO::getNome)
                .contains(eletrico);
    }

    @Test
    public void deve_buscar_todos_os_tipo_cadastrados() throws Exception {

        int quantidadeEsperada = 3;
        String eletrico = "Eletrico";
        String agua = "Agua";
        String fantasma = "Fantasma";

        tipoRepository.save(new Tipo(eletrico));
        tipoRepository.save(new Tipo(agua));
        tipoRepository.save(new Tipo(fantasma));

        MvcResult resultado = mvc.perform(get("/api/v1/tipos")).andReturn();

        TipoResponseDTO[] tiposRetornados = JsonUtil.mapFromJson(resultado.getResponse()
                .getContentAsString(), TipoResponseDTO[].class);

        assertThat(tiposRetornados.length).isEqualTo(quantidadeEsperada);

        assertThat(HttpStatus.OK.value()).isEqualTo(resultado.getResponse().getStatus());

        assertThat(tiposRetornados).extracting(TipoResponseDTO::getNome).contains(eletrico);
    }

    @Test
    public void deve_deletar_um_tipo() throws Exception{
        //Codigo Do Enzão
        int quantidadeEsperada = 3;
        String eletrico = "Eletrico";
        String agua = "Agua";
        String fantasma = "Fantasma";
        Tipo tipoAgua = new Tipo(agua);
        tipoRepository.save(tipoAgua);
        tipoRepository.save(new Tipo(agua));
        tipoRepository.save(new Tipo(eletrico));
        tipoRepository.save(new Tipo(fantasma));

        String url = "/api/v1/tipos/" + tipoAgua.getId();
        MvcResult resultado = mvc.perform(delete(url)).andReturn();

        Iterable<Tipo> tiposEncontrados = tipoRepository.findAll();
        long quantidadeEncontrada = tiposEncontrados.spliterator().getExactSizeIfKnown();

        assertThat(quantidadeEncontrada)
                .isEqualTo(quantidadeEsperada);

        assertThat(HttpStatus.NO_CONTENT.value())
                .isEqualTo(resultado.getResponse().getStatus());
    }
}
