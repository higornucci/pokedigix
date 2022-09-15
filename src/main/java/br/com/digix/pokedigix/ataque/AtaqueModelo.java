package br.com.digix.pokedigix.ataque;

public abstract class AtaqueModelo {
    protected int forca;
    protected int acuracia;
    protected int pontosDePoder;
    protected Categoria categoria;
    protected String nome;
    protected String descricao;

    public int getForca() {
        return forca;
    }

    public void setForca(int forca) {
        this.forca = forca;
    }

    public int getAcuracia() {
        return acuracia;
    }

    public void setAcuracia(int acuracia) {
        this.acuracia = acuracia;
    }

    public int getPontosDePoder() {
        return pontosDePoder;
    }

    public void setPontosDePoder(int pontosDePoder) {
        this.pontosDePoder = pontosDePoder;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
