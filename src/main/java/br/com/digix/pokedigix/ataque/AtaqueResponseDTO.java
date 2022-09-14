package br.com.digix.pokedigix.ataque;

import br.com.digix.pokedigix.tipo.TipoResponseDTO;

public class AtaqueResponseDTO extends AtaqueModelo {
    private Long id;
    private TipoResponseDTO tipo;

    public AtaqueResponseDTO() {
    }

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

    public TipoResponseDTO getTipo() {
        return tipo;
    }

    public void setTipo(TipoResponseDTO tipo) {
        this.tipo = tipo;
    }

}
