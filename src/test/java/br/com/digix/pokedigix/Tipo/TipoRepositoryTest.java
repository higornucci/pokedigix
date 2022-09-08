package br.com.digix.pokedigix.tipo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
 class TipoRepositoryTest {

    @Autowired
    private TipoRepository tipoRepository;

    @Test
     void deve_salvar_um_tipo() {
        String nomeEsperado = "Fantasma";
        Tipo tipo = new Tipo(nomeEsperado);

        tipoRepository.save(tipo);

        assertNotNull(tipo.getId());
    }

    @Test
     void deve_buscar_um_tipo_pelo_nome() {
        String nome = "Eletrico";
        Tipo tipo = new Tipo(nome);
        tipoRepository.save(tipo);

        Collection<Tipo> tiposRetornados = tipoRepository.findByNomeContaining(nome);

        assertTrue(tiposRetornados.contains(tipo));
    }

    @Test
     void deve_buscar_um_tipo_pelo_nome_parcial() {
        String nome = "Eletrico";
        String nomeParcial = "Ele";
        Tipo tipo = new Tipo(nome);
        tipoRepository.save(tipo);

        Collection<Tipo> tiposRetornados = tipoRepository.findByNomeContaining(nomeParcial);

        assertTrue(tiposRetornados.contains(tipo));
    }

    @Test
     void deve_poder_remover_pelo_nome() {
        String fada = "Fada";
        String fantasma = "Fantasma";
        tipoRepository.save(new Tipo(fada));
        tipoRepository.save(new Tipo(fantasma));
        String termo = "Fa";
        Long quantidadeEsperada = 2l;

        Long quantidadeRemovida = tipoRepository.deleteByNomeContaining(termo);

        assertEquals(quantidadeEsperada, quantidadeRemovida);
    }

   
}
