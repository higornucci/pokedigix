package br.com.digix.pokedigix.Tipo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRepository;

@DataJpaTest
public class TipoRepositoryTest {
    @Autowired
    private TipoRepository tipoRepository;

    @Test
    public void deve_buscar_um_tipo_pelo_nome() {
        // Arrange
        String nomeEsperado = "Fantasma";
        Tipo tipoSalvo = new Tipo(nomeEsperado);
        tipoRepository.save(tipoSalvo);

        // Act
        Tipo tipoRetornado = tipoRepository.findByNome(nomeEsperado).get(0);

        // Assert
        assertEquals(nomeEsperado, tipoRetornado.getNome());
    }
}
