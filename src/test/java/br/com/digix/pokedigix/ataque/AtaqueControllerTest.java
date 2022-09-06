package br.com.digix.pokedigix.ataque;

import static org.assertj.core.api.Assertions.assertThat;
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
import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRepository;
import br.com.digix.pokedigix.utils.JsonUtil;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PokedigixApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase

public class AtaqueControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private AtaqueRepository ataqueRepository;

    @Autowired
    private TipoRepository tipoRepository;

    @AfterEach
    public void resetDb() {
        ataqueRepository.deleteAll();
    }

    @Test
    void deve_adicionar_um_ataque() throws Exception {
        // Arrenge
        int quantidadeEsperada = 1;
        String eletrico = "Eletrico";
        Tipo tipoEletrico = new Tipo(eletrico);
        tipoRepository.save(tipoEletrico);
        AtaqueRequestDTO ataqueRequestDTO = new AtaqueRequestDTO();
        ataqueRequestDTO.setNome("choque do trovao");
        ataqueRequestDTO.setAcuracia(100);
        ataqueRequestDTO.setCategoria(Categoria.ESPECIAL);
        ataqueRequestDTO.setForca(100);
        ataqueRequestDTO.setPontosDePoder(100);
        ataqueRequestDTO.setDescricao("ataque");
        ataqueRequestDTO.setTipoId(tipoEletrico.getId());
        String descricaoEsperada = "ataque";

        // Action
        mvc.perform(post("/api/v1/ataques/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(ataqueRequestDTO)))
                .andExpect(status().isCreated());

        // Asserts
        Iterable<Ataque> ataquesEncontrados = ataqueRepository.findAll();
        long quantidadeEncontrada = ataquesEncontrados.spliterator().getExactSizeIfKnown();

        assertThat(quantidadeEncontrada).isEqualTo(quantidadeEsperada);
        assertThat(ataquesEncontrados).extracting(Ataque::getDescricao).containsOnly(descricaoEsperada);
    }
}
