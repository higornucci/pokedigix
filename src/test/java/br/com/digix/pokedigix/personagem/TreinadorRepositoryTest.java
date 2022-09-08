package br.com.digix.pokedigix.personagem;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class TreinadorRepositoryTest {

    @Autowired
    private TreinadorRepository treinadorRepository;

    @Test
    void deve_salvar_um_treinador() throws Exception {
        Treinador treinador = new TreinadorBuilder().construir();

        treinadorRepository.save(treinador);

        assertNotNull(treinador.getId());
    }
}
