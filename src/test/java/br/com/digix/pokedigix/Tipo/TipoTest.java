package br.com.digix.pokedigix.tipo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TipoTest {
<<<<<<< HEAD
    @Test
    void deve_poder_criar_um_tipo() {
=======
>>>>>>> dev

  @Test
  void deve_poder_criar_um_tipo() {
    String nomeEsperado = "Fantasma";

    Tipo tipo = new Tipo(nomeEsperado);

<<<<<<< HEAD
        assertEquals(nomeEsperado, tipo.getNome());
    }

    
    
=======
    assertEquals(nomeEsperado, tipo.getNome());
  }
>>>>>>> dev
}
