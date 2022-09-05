package br.com.digix.pokedigix.ataque;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
    @BeforeEach
    public void resetDb() {
        ataqueRepository.deleteAll();
        tipoRepository.deleteAll();
    }

    @Test
    public void deve_adicionar_um_ataque() throws Exception {
        // Arrange
        int quantidadeEsperada = 1;
        String nometipo = "Normal";
        Tipo tipo = new Tipo(nometipo);
        tipoRepository.save(tipo);
        String nomeAtaque = "Choque do trovão";
        AtaqueRequestDTO ataqueRequestDTO = new AtaqueRequestDTO();
        ataqueRequestDTO.setTipoId(tipo.getId());
        ataqueRequestDTO.setAcuracia(10);
        ataqueRequestDTO.setCategoria(Categoria.FISICO);
        ataqueRequestDTO.setDescricao("Da choque nos outros");
        ataqueRequestDTO.setForca(30);
        ataqueRequestDTO.setNome(nomeAtaque);
        ataqueRequestDTO.setPontosDePoder(20);

        // Act
        MvcResult resultado = mvc.perform(post("/api/v1/ataques/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.toJson(ataqueRequestDTO))).andReturn();

        // Asserts
        int status = resultado.getResponse().getStatus();
        assertEquals(HttpStatus.CREATED.value(), status);

        Iterable<Ataque> ataquesEncontrados = ataqueRepository.findAll();
        long quantidadeEncontrada = ataquesEncontrados.spliterator().getExactSizeIfKnown();
        assertThat(quantidadeEncontrada).isEqualTo(quantidadeEsperada);
        assertThat(ataquesEncontrados).extracting(Ataque::getNome).containsOnly(nomeAtaque);
    }
}
