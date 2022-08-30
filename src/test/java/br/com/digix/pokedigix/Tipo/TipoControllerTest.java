package br.com.digix.pokedigix.tipo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import br.com.digix.pokedigix.PokedigixApplication;
import br.com.digix.pokedigix.utils.JsonUtil;

@ExtendWith(SpringExtension.class)
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

        mvc.perform(post("/api/v1/tipos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(tipoRequestDTO)))
                .andExpect(status().isCreated());

        Iterable<Tipo> tiposEncontrados = tipoRepository.findAll();
        Long quantidadeEncontrada = tiposEncontrados.spliterator().getExactSizeIfKnown();
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

        MvcResult mvcResult = mvc
                .perform(get("/api/v1/tipos/" + tipo.getId())).andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK, status);

        String content = mvcResult.getResponse().getContentAsString();
        TipoResponseDTO tipoDTO = JsonUtil.mapFromJson(content, TipoResponseDTO.class);

        assertThat(tipoDTO.getId()).isEqualTo(tipo.getId());
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

        assertThat(tiposRetornados.length)
                .isEqualTo(quantidadeEsperada);

        assertThat(HttpStatus.OK.value())
                .isEqualTo(resultado.getResponse().getStatus());

        assertThat(tiposRetornados)
                .extracting(TipoResponseDTO::getNome)
                .contains(eletrico);
    }

}
