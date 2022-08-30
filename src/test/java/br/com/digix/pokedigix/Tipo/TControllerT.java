package br.com.digix.pokedigix.tipo;

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

import br.com.digix.pokedigix.PokedigixApplication;
import br.com.digix.pokedigix.utils.JsonUtil;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PokedigixApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class TControllerT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TipoRepository tipoRepository;

    @AfterEach
    public void resetDb(){
        tipoRepository.deleteAll();
    }

    @Test
    public void deve_adicionar_um_tipo() throws Exception{
        String nomeEsperado = "Fogo";
        TipoRequestDTO tipoRequestDTO =  new TipoRequestDTO(nomeEsperado);

        mvc.perform(post("/api/v1/tipos").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(tipoRequestDTO)));


        Iterable<Tipo> tiposEncontrados = tipoRepository.findAll();
        Assertions.assertThat(tiposEncontrados).extracting(Tipo::getNome).containsOnly(nomeEsperado);
    }

    @Test
    public void deve_buscar_um_tipo_pelo_id() throws Exception{
        String nomeEsperado = "Fogo";
        Tipo tipo = new Tipo(nomeEsperado);

        tipoRepository.save(tipo);

    }
}