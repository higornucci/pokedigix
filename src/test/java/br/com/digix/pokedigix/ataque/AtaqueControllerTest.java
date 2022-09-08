package br.com.digix.pokedigix.ataque;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @BeforeEach
    @AfterEach
    public void resetDb() {
        ataqueRepository.deleteAll();
        tipoRepository.deleteAll();
    }

    @Test
    public void deve_adicionar_um_ataque() throws Exception {
        String ataqueEsperado = "Choque do Trovao";
        String tipoEsperado = "ELetrico";

        int quantidadeDeAtaquesEsperados = 1;
        Tipo tipo = new Tipo(tipoEsperado);
        tipoRepository.save(tipo);

        AtaqueRequestDTO ataqueRequestDTO = new AtaqueRequestDTO();
        ataqueRequestDTO.setForca(125);
        ataqueRequestDTO.setAcuracia(100);
        ataqueRequestDTO.setPontosDePoder(99);
        ataqueRequestDTO.setCategoria(Categoria.ESPECIAL);
        ataqueRequestDTO.setNome(ataqueEsperado);
        ataqueRequestDTO.setDescricao("Muito Bom");
        ataqueRequestDTO.setTipoId(tipo.getId());

        mvc.perform(
                post("/api/v1/ataques").contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toJson(ataqueRequestDTO)));

        Iterable<Ataque> ataquesEncontrados = ataqueRepository.findAll();
        long quantidadeDeAtaquesEncontrados = ataquesEncontrados.spliterator().getExactSizeIfKnown();

        mvc.perform(post("/api/v1/ataques")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(ataqueRequestDTO)))
                .andExpect(status().isCreated());

        assertThat(quantidadeDeAtaquesEncontrados)
                .isEqualTo(quantidadeDeAtaquesEsperados);

        assertThat(ataquesEncontrados)
                .extracting(Ataque::getNome)
                .containsOnly(ataqueEsperado);
    }

    @Test
    void deve_excluir_um_ataque_pelo_id() throws Exception {
        // Teste do código Do Enzão
        int quantidadeEsperada = 0;

        Tipo tipo = new Tipo("Eletrico");

        Ataque ataque = new AtaqueBuilder().comTipo(tipo).construir();
        ataqueRepository.save(ataque);

        String url = "/api/v1/ataques/" + ataque.getId();
        mvc.perform(delete(url)).andReturn();

        Iterable<Ataque> ataquesEncontrados = ataqueRepository.findAll();
        long quantidadeEncontrada = ataquesEncontrados.spliterator().getExactSizeIfKnown();

        assertEquals(quantidadeEsperada, quantidadeEncontrada);
    }
}
