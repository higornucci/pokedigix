package br.com.digix.pokedigix.Tipo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.com.digix.pokedigix.tipo.Tipo;

public class TipoTest {
    @Test
    public void deve_poder_criar_um_tipo() {

        String nomeEsperado = "Fantasma";

        Tipo tipo = new Tipo(nomeEsperado);

        assertEquals(nomeEsperado, tipo.getNome());
    }
}
