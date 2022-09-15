package br.com.digix.pokedigix.ataque;

public class AtaqueRequestDTO extends AtaqueDTO {

    private Long tipoId;

    public AtaqueRequestDTO() {
    }

    public AtaqueRequestDTO(
            int forca, 
            int acuracia,
            int pontosDePoder,
            Long tipoId,
            Categoria categoria, 
            String nome, 
            String descricao) {
        super(forca, acuracia, pontosDePoder, categoria, nome, descricao);
        this.tipoId = tipoId;
    }

    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }
}
