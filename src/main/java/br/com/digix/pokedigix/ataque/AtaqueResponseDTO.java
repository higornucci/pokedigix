package br.com.digix.pokedigix.ataque;

import br.com.digix.pokedigix.tipo.TipoResponseDTO;

public class AtaqueResponseDTO {

    private Long id;
    private int forca;
    private int acuracia;
    private int pontosDePoder;
    private Categoria categoria;
    private String nome;
    private String descricao;
    private TipoResponseDTO tipo;

    public AtaqueResponseDTO(){}
    
    public AtaqueResponseDTO(
            Long id,
            int forca,
            int acuracia,
            int pontosDePoder,
            Categoria categoria,
            String nome,
            String descricao,
            TipoResponseDTO tipo) {
                
        this.id = id;
        this.forca = forca;
        this.acuracia = acuracia;
        this.pontosDePoder = pontosDePoder;
        this.categoria = categoria;
        this.nome = nome;
        this.descricao = descricao;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public TipoResponseDTO getTipo() {
        return tipo;
    }

    public void setTipo(TipoResponseDTO tipo) {
        this.tipo = tipo;
    }

}
