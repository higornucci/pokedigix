package br.com.digix.pokedigix.tipo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class TipoRepositoryTest {

    @Autowired
    private TipoRepository tipoRepository;

    @Test
    public void deve_salvar_um_tipo() {
        String nomeEsperado = "Fantasma";
        Tipo tipo = new Tipo(nomeEsperado);

        tipoRepository.save(tipo);

        assertNotNull(tipo.getId());
    }

    @Test
    public void deve_buscar_um_tipo_pelo_nome() {
        String nome = "Eletrico";
        Tipo tipo = new Tipo(nome);
        tipoRepository.save(tipo);

        Collection<Tipo> tiposRetornados = tipoRepository.findByNomeContaining(nome);

        assertTrue(tiposRetornados.contains(tipo));
    }

    @Test
    public void deve_buscar_um_tipo_pelo_nome_parcial() {
        String nome = "Eletrico";
        String nomeParcial = "Ele";
        Tipo tipo = new Tipo(nome);
        tipoRepository.save(tipo);

        Collection<Tipo> tiposRetornados = tipoRepository.findByNomeContaining(nomeParcial);

        assertTrue(tiposRetornados.contains(tipo));
    }

    @Test
    public void deve_poder_remover_pelo_nome() {
        String fada = "Fada";
        String fantasma = "Fantasma";
        tipoRepository.save(new Tipo(fada));
        tipoRepository.save(new Tipo(fantasma));
        String termo = "Fa";
        Long quantidadeEsperada = 2l;

        Long quantidadeRemovida = tipoRepository.deleteByNomeContaining(termo);

        assertEquals(quantidadeEsperada, quantidadeRemovida);
    }

    /* @Test
    public void deve_alterar_um_tipo() throws Exception {
            // Arrange
            String fantasma = "Fantasma";
            Tipo tipoFantasma = new Tipo(fantasma);
            String nomeNovo = "Capitão America";
            tipoRepository.save(tipoFantasma);
            tipoRepository.save(new Tipo("Homem aranha"));


            TipoRequestDTO tipoRequestDTO = new TipoRequestDTO(nomeNovo);

            // Action
            MvcResult resultado = mvc.perform(put("/api/v1/tipos/" + tipoFantasma.getId()).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(tipoRequestDTO))).andReturn();

            // Asserts
            Tipo tiposEncontrados = tipoRepository.findById(tipoFantasma.getId()).get();
            
            assertThat(tiposEncontrados.getNome()).isEqualTo(nomeNovo);

            assertThat(HttpStatus.OK.value())
            .isEqualTo(resultado.getResponse().getStatus());
    }
 */
}
