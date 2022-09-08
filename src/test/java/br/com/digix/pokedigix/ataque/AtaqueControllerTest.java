package br.com.digix.pokedigix.ataque;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import br.com.digix.pokedigix.utils.JsonUtil;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.digix.pokedigix.tipo.TipoRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import br.com.digix.pokedigix.PokedigixApplication;
import br.com.digix.pokedigix.tipo.Tipo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, classes = PokedigixApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
 class AtaqueControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AtaqueRepository ataqueRepository;
    @Autowired
    private TipoRepository tipoRepository;
    

    @AfterEach
     void reserDb(){
        ataqueRepository.deleteAll();
    }

    @Test
     void deve_adicionar_um_ataque()throws Exception{
        //Arrange
        int quantidadeDeAtaquesEsperada = 1;
        String nometipo = "Normal";
        String nomeAtaque = "Choque do trovão";
        
        Tipo tipo = new Tipo(nometipo);
        
        tipoRepository.save(tipo);
        
        AtaqueRequestDTO ataqueRequestDTO = new AtaqueRequestDTO();
        ataqueRequestDTO.setAcuracia(30);
        ataqueRequestDTO.setTipoId(tipo.getId());
        ataqueRequestDTO.setCategoria(Categoria.ESPECIAL);
        ataqueRequestDTO.setDescricao("Ataca Rapido");
        ataqueRequestDTO.setForca(30);
        ataqueRequestDTO.setNome(nomeAtaque);
        ataqueRequestDTO.setPontosDePoder(80);
        
        //Action
        mvc.perform(post("/api/v1/ataques" ).contentType(MediaType.APPLICATION_JSON) 
        .content(JsonUtil.toJson(ataqueRequestDTO)))
        .andExpect(status().isCreated());
        
        //Asserts
        Iterable<Ataque> ataquesEncontrados = ataqueRepository.findAll();
        long quantidadeEncontrada = ataquesEncontrados.spliterator().getExactSizeIfKnown();

        assertThat(quantidadeEncontrada)
                .isEqualTo(quantidadeDeAtaquesEsperada);

        assertThat(ataquesEncontrados)
                .extracting(Ataque::getNome)
                .containsOnly(nomeAtaque);

    }
    
}
