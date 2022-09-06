package br.com.digix.pokedigix.personagem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

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

	@Test
	public void deve_buscar_um_endereco_pelo_id() throws Exception {
			String cidade = "Pallet";
			String regiao = "Kanto";
			Endereco endereco = new Endereco(regiao, cidade);
			enderecoRepository.save(endereco);

			MvcResult mvcResult = mvc.perform(get("/api/v1/enderecos/" + endereco.getId())).andReturn();

			int status = mvcResult.getResponse().getStatus();

			assertEquals(HttpStatus.OK.value(), status);

			String content = mvcResult.getResponse().getContentAsString();
			EnderecoResponseDTO enderecoResponseDTO = JsonUtil.mapFromJson(content, EnderecoResponseDTO.class);

			assertThat(enderecoResponseDTO.getId()).isEqualTo(endereco.getId());

	}
}
