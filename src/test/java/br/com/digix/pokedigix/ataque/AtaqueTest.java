package br.com.digix.pokedigix.ataque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import br.com.digix.pokedigix.tipo.Tipo;

class AtaqueTest {

    @Test
    void deve_criar_um_ataque()
            throws AcuraciaInvalidaException, ForcaInvalidaParaCategoriaException, TipoInvalidoParaCategoriaException {
        int forca = 40;
        int acuracia = 100;
        int pontosDePoder = 35;
        Categoria categoria = Categoria.FISICO;
        String nome = "Ataque Rapido";
        String descricao = "O usuario ataque antes do oponente.";
        Tipo tipoEsperado = new Tipo("Normal");

        Ataque ataque = new Ataque(forca, acuracia, pontosDePoder, categoria, nome, descricao, tipoEsperado);
        assertEquals(forca, ataque.getForca());
        assertEquals(acuracia, ataque.getAcuracia());
        assertEquals(pontosDePoder, ataque.getPontosDePoder());
        assertEquals(categoria, ataque.getCategoria());
        assertEquals(nome, ataque.getNome());
        assertEquals(descricao, ataque.getDescricao());
    }

    @Test
    void deve_ser_obrigatorio_informar_um_tipo() throws Exception {
        Tipo tipoEsperado = new Tipo("Normal");
        Ataque ataque = new AtaqueBuilder().comTipo(tipoEsperado).construir();

        assertEquals(tipoEsperado, ataque.getTipo());
    }

    @Test
    void nao_deve_ter_acuracia_menor_que_zero() {
        // Arrange
        int acuracia = -1;

        // Assert
        assertThrows(
                AcuraciaInvalidaException.class,
                () -> {
                    // Action
                    new AtaqueBuilder().comAcuracia(acuracia).construir();
                });

        // Assert
        assertThrows(
                AcuraciaInvalidaException.class,
                () -> {
                    // Action
                    new AtaqueBuilder()
                            .comAcuracia(acuracia)
                            .comCategoriaEfeito()
                            .construir();
                });
    }

    @Test
    void nao_deve_ter_acuracia_maior_que_cem() {
        // Arrange
        int acuracia = 101;

        // Assert
        assertThrows(
                AcuraciaInvalidaException.class,
                () -> {
                    // Action
                    new AtaqueBuilder().comAcuracia(acuracia).construir();
                });

        // Assert
        assertThrows(
                AcuraciaInvalidaException.class,
                () -> {
                    // Action
                    new AtaqueBuilder()
                            .comAcuracia(acuracia)
                            .comCategoriaEfeito()
                            .construir();
                });
    }

    @Test
    void deve_poder_ter_acuracia_igual_a_zero() throws Exception {
        int acuraciaEsperada = 0;

        Ataque ataque = new AtaqueBuilder()
                .comAcuracia(acuraciaEsperada)
                .construir();

        assertEquals(acuraciaEsperada, ataque.getAcuracia());
    }

    @Test
    void nao_deve_ter_forca_nem_tipo_quando_a_categoria_for_efeito() throws Exception {
        Categoria categoria = Categoria.EFEITO;
        int forca = 0;
        Tipo tipo = null;

        Ataque ataque = new AtaqueBuilder().comCategoriaEfeito().construir();

        assertEquals(categoria, ataque.getCategoria());
        assertEquals(forca, ataque.getForca());
        assertEquals(tipo, ataque.getTipo());
    }

    @Test
    void nao_deve_ter_ataque_de_categoria_fisica_sem_forca() {
        Categoria categoria = Categoria.FISICO;
        int forca = 0;
        assertThrows(
                ForcaInvalidaParaCategoriaException.class,
                () -> {
                    new AtaqueBuilder().comForca(forca).comCategoria(categoria).construir();
                });
    }

    @Test
    void nao_deve_ter_ataque_de_categoria_fisica_sem_tipo() {
        Categoria categoria = Categoria.FISICO;
        Tipo tipo = null;

        assertThrows(
                TipoInvalidoParaCategoriaException.class,
                () -> {
                    new AtaqueBuilder().comTipo(tipo).comCategoria(categoria).construir();
                });
    }

    @Test
    void nao_deve_ter_ataque_de_categoria_especial_sem_forca() {
        Categoria categoria = Categoria.ESPECIAL;
        int forca = 0;

        assertThrows(
                ForcaInvalidaParaCategoriaException.class,
                () -> {
                    new AtaqueBuilder().comForca(forca).comCategoria(categoria).construir();
                });
    }

    @Test
    void nao_deve_ter_ataque_de_categoria_especial_sem_tipo() {
        Categoria categoria = Categoria.ESPECIAL;
        Tipo tipo = null;

        assertThrows(TipoInvalidoParaCategoriaException.class, () -> {
            new AtaqueBuilder().comTipo(tipo).comCategoria(categoria).construir();
        });
    }

    @Test
    void deve_alterar_um_ataque() throws Exception {
        Ataque ataque = new AtaqueBuilder().construir();
        int forca = 4;
        int acuracia = 10;
        int pontosDePoder = 5;
        Categoria categoria = Categoria.EFEITO;
        String nome = "Ataque Lento";
        String descricao = "O usuario ataque depois do oponente.";
        Tipo tipoEsperado = new Tipo("Ghost");

        ataque.setAcuracia(acuracia);
        ataque.setCategoria(categoria);
        ataque.setDescricao(descricao);
        ataque.setForca(forca);
        ataque.setPontosDePoder(pontosDePoder);
        ataque.setTipo(tipoEsperado);
        ataque.setNome(nome);

        // Ataque ataque = new Ataque(forca, acuracia, pontosDePoder, categoria, nome,
        // descricao, tipoEsperado);

        assertEquals(forca, ataque.getForca());
        assertEquals(acuracia, ataque.getAcuracia());
        assertEquals(pontosDePoder, ataque.getPontosDePoder());
        assertEquals(categoria, ataque.getCategoria());
        assertEquals(nome, ataque.getNome());
        assertEquals(descricao, ataque.getDescricao());
        assertEquals(tipoEsperado, ataque.getTipo());
    }

}
