package br.com.digix.pokedigix.personagem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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

    @BeforeEach
    @AfterEach
    public void resetDb() {
        enderecoRepository.deleteAll();
    } 

    @Test
    public void deve_buscar_pelo_nome_da_cidade() throws Exception{
        String cidade = "Pallet";
        
        Endereco endereco = new EnderecoBuilder().comCidade(cidade).construir();
        enderecoRepository.save(endereco);

        //Action
        MvcResult resultado = mvc.perform(get("/api/v1/enderecos/cidade?" + endereco.getCidade())).andReturn();

        //Assertions
        EnderecoResponseDTO[] enderecosRetornadosDTO = JsonUtil.mapFromJson(resultado.getResponse().getContentAsString(),EnderecoResponseDTO[].class);

        assertThat(HttpStatus.OK.value()).isEqualTo(resultado.getResponse().getStatus());
        assertThat(enderecosRetornadosDTO).extracting("cidade").contains(cidade);
    }

    @Test
    public void deve_buscar_pelo_nome_da_regiao() throws Exception{
        String regiao = "Kanto";
        Endereco endereco = new EnderecoBuilder().comRegiao(regiao).construir();
        enderecoRepository.save(endereco);

        //Action
        MvcResult resultado = mvc.perform(get("/api/v1/enderecos/regiao?" + endereco.getId())).andReturn();

        //Assertions
        EnderecoResponseDTO[] enderecosRetornadosDTO = JsonUtil.mapFromJson(resultado.getResponse().getContentAsString(), EnderecoResponseDTO[].class);

        assertThat(HttpStatus.OK.value()).isEqualTo(resultado.getResponse().getStatus());
        assertThat(enderecosRetornadosDTO).extracting("cidade").contains(regiao);
    }

}
