package br.com.digix.pokedigix.ataque;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.com.digix.pokedigix.tipo.Tipo;

public class AtaqueTest {
    
    @Test
    public void deve_criar_um_ataque() {
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
    public void deve_ser_obrigatorio_informar_um_tipo() {
        Tipo tipoEsperado = new Tipo("Normal");
        Ataque ataque = new AtaqueBuilder().comTipo(tipoEsperado).construir();

        assertEquals(tipoEsperado, ataque.getTipo());
    }
}
