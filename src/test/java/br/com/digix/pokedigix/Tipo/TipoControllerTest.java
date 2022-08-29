package br.com.digix.pokedigix.tipo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

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
		String nome = "Fire";
		TipoRequestDTO tipoRequestDTO = new TipoRequestDTO(nome);
        mvc.perform(post("/api/v1/tipos").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(tipoRequestDTO)));

        Iterable<Tipo> tiposEncontrados = tipoRepository.findAll();
        Assertions.assertThat(tiposEncontrados).extracting(Tipo::getNome).containsOnly(nome);
	}

    @Test
	public void deve_buscar_um_tipo_pelo_id() throws Exception {
		String nome = "Fire";
        Tipo tipo = new Tipo(nome);
        tipoRepository.save(tipo);

        MvcResult mvcResult = mvc.
            perform(MockMvcRequestBuilders.get("/api/v1/tipos/" + tipo.getId()).
            accept(MediaType.APPLICATION_JSON_VALUE)).
            andReturn();

        int status = mvcResult.getResponse().getStatus();
        assertEquals(200, status);

        String content = mvcResult.getResponse().getContentAsString();
        TipoResponseDTO tipoDTO = JsonUtil.mapFromJson(content, TipoResponseDTO.class);

        Assertions.assertThat(tipoDTO.getId()).isEqualTo(tipo.getId());
	}
    
}
