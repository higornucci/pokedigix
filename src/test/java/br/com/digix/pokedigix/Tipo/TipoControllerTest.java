package br.com.digix.pokedigix.tipo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.assertj.core.api.Assertions;
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

import com.jayway.jsonpath.JsonPath;

import br.com.digix.pokedigix.PokedigixApplication;
import br.com.digix.pokedigix.utils.JsonUtil;
import ch.qos.logback.core.status.Status;

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
        //Action
        mvc.perform(post("/api/v1/tipos")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.toJson(tipoRequestDTO)))
        .andExpect(status().isCreated());
       
        //Asserts
        Iterable<Tipo> tiposEncontrados = tipoRepository.findAll();
        long quantidadeEncontrada = tiposEncontrados.spliterator().getExactSizeIfKnown();
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

        MvcResult mvcResult = mvc.
            perform(get("/api/v1/tipos/" + tipo.getId()).
            accept(MediaType.APPLICATION_JSON_VALUE)).
            andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(HttpStatus.OK, status);

        String content = mvcResult.getResponse().getContentAsString();
        TipoResponseDTO tipoDTO = JsonUtil.mapFromJson(content, TipoResponseDTO.class);

        Assertions.assertThat(tipoDTO.getId()).isEqualTo(tipo.getId());
	}

    @Test
    public void deve_buscar_todos_os_tipos_cadastrados() throws Exception{
        //Arrange
        String eletrico = "Eletrico";
        String agua = "Agua";
        String fantasma = "Fantasma";
        tipoRepository.save(new Tipo(eletrico));
        tipoRepository.save(new Tipo(agua));
        tipoRepository.save(new Tipo(fantasma));
        int quantidadeEsperada = 3;

        //Action
       MvcResult resutado =  mvc.perform(get( "/api/v1/tipos")).andReturn();

       //Assert
       TipoResponseDTO[] tiposRetornados = 
       JsonUtil.mapFromJson(resutado.getResponse() 
       .getContentAsString(), TipoResponseDTO[].class);

    assertThat(tiposRetornados.length)
       .isEqualTo(quantidadeEsperada);

       assertThat(HttpStatus.OK.value()) 
       .isEqualTo(resutado.getResponse().getStatus());

       assertThat(tiposRetornados)
       .extracting(TipoResponseDTO::getNome)
       .contains(eletrico);
    }
    
}
