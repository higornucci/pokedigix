package br.com.digix.pokedigix.ataque;

public class Ataque {

    private int forca;
    private int acuracia;
    private int pontosDePoder;
    private Categoria categoria;
    private String nome;
    private String descricao;

    public Ataque(int forca, int acuracia, int pontosDePoder, Categoria categoria, String nome, String descricao) {
        this.forca = forca;
        this.acuracia = acuracia;
        this.pontosDePoder = pontosDePoder;
        this.categoria = categoria;
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getForca() {
        return forca;
    }

    public int getAcuracia() {
        return acuracia;
    }

    public int getPontosDePoder() {
        return pontosDePoder;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }
    
}
