package br.com.digix.pokedigix.ataque;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void resetDb() {
        tipoRepository.deleteAll();
    }
    
    @Test
    public void deve_adicionar_um_ataque() throws Exception{
        int quantidadeEsperada = 1;
        Tipo tipoEsperado = new Tipo("Normal");
        tipoRepository.save(tipoEsperado);
        long idTipo = tipoEsperado.getId();
        int forca = 60;
        int acuracia = 80;
        int pontosDePoder = 74;
        Categoria categoria = Categoria.FISICO;
        String nome = "Ataque Rapido";
        String descricao = "ataque na pança";


        AtaqueRequestDTO ataqueRequestDTO = new AtaqueRequestDTO();

        ataqueRequestDTO.setTipoId(idTipo);
        ataqueRequestDTO.setForca(forca);
        ataqueRequestDTO.setAcuracia(acuracia);
        ataqueRequestDTO.setPontosDePoder(pontosDePoder);
        ataqueRequestDTO.setCategoria(categoria);
        ataqueRequestDTO.setNome(nome);
        ataqueRequestDTO.setDescricao(descricao);

        mvc.perform(post("/api/v1/ataques/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(JsonUtil.toJson(ataqueRequestDTO)))
        .andExpect(status().isCreated());

        Iterable<Ataque> ataquesEncontrados = ataqueRepository.findAll();
        long quantidadeEncontrada = ataquesEncontrados.spliterator().getExactSizeIfKnown();

        assertThat(quantidadeEncontrada).isEqualTo(quantidadeEsperada);
        assertThat(ataquesEncontrados).extracting(Ataque::getNome).contains(nome);


    }
}
