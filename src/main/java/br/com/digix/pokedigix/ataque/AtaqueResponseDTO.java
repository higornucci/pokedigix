package br.com.digix.pokedigix.ataque;

import br.com.digix.pokedigix.tipo.TipoResponseDTO;

public class AtaqueResponseDTO extends AtaqueDTO {

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
        super(forca, acuracia, pontosDePoder, categoria, nome, descricao);
        this.id = id;
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