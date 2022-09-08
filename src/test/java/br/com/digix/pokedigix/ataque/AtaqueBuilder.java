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
        this.acuracia = 90;
        this.pontosDePoder = 35;
        this.categoria = Categoria.FISICO;
        this.nome = "Ataque Rapido";
        this.descricao = "O usuario ataque antes.";
        this.tipoEsperado = new Tipo("Normal");
    }

    public AtaqueBuilder comTipo(Tipo tipoEsperado) {
        this.tipoEsperado = tipoEsperado;
        return this;
    }
    
    public AtaqueBuilder comAcuracia(int acuracia) {
        this.acuracia = acuracia;
        return this;
    }

    public Ataque construir() throws Exception {
        if(this.categoria.equals(Categoria.EFEITO)) {
            return new Ataque(acuracia, pontosDePoder, nome, descricao);
        } else {
            return new Ataque(forca, acuracia, pontosDePoder, categoria, nome, descricao, tipoEsperado);
        }
    }

    public AtaqueBuilder comCategoriaEfeito() {
        this.categoria = Categoria.EFEITO;
        return this;
    }

    public AtaqueBuilder comForca(int forca) {
        this.forca = forca;
        return this;
    }
    
    public AtaqueBuilder comCategoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    } 
}
