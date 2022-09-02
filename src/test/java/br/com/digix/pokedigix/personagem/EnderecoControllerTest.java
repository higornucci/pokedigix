package br.com.digix.pokedigix.personagem;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import br.com.digix.pokedigix.PokedigixApplication;

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
        MvcResult resultado = mvc.perform(delete(url)).andReturn();

        Iterable<Endereco> enderecosEncontrados = enderecoRepository.findAll();
        long quantidadeEncontrada = enderecosEncontrados.spliterator().getExactSizeIfKnown();

        assertEquals(quantidadeEsperada, quantidadeEncontrada);
    }
}
