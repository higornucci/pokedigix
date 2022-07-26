package br.com.digix.pokedigix.ataque;

import br.com.digix.pokedigix.tipo.Tipo;

public class AtaqueBuilder {

    private int forca;
    private int acuracia;
    private int pontosDePoder;
    private Categoria categoria;
    private String nome;
    private String descricao;
    private Tipo tipoEsperado;

    public AtaqueBuilder() {
        this.forca = 40;
        this.acuracia = 100;
        this.pontosDePoder = 35;
        this.categoria = Categoria.FISICO;
        this.nome = "Ataque Rapido";
        this.descricao = "O usuario ataque antes do oponente.";
        this.tipoEsperado = new Tipo("Normal");
    }

    public AtaqueBuilder comTipo(Tipo tipoEsperado) {
        this.tipoEsperado = tipoEsperado;
        return this;
    }
    
    public Ataque construir() {
        return new Ataque(forca, acuracia, pontosDePoder, categoria, nome, descricao, tipoEsperado);
    }
    
}
