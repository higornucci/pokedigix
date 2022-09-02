package br.com.digix.pokedigix.personagem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import br.com.digix.pokedigix.utils.JsonUtil;

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
    public void deve_atualizar_um_endereco() throws Exception {
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

}
