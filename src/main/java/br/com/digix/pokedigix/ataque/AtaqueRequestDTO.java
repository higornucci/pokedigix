package br.com.digix.pokedigix.ataque;

public class AtaqueRequestDTO extends AtaqueModelo {
    protected Long tipoId;

    public AtaqueRequestDTO() {
    }

    public AtaqueRequestDTO(int forca, int acuracia, int pontosDePoder, Long tipoId, Categoria categoria, String nome,
            String descricao) {
        this.forca = forca;
        this.acuracia = acuracia;
        this.pontosDePoder = pontosDePoder;
        this.tipoId = tipoId;
        this.categoria = categoria;
        this.nome = nome;
        this.descricao = descricao;
    }

    public Long getTipoId() {
        return tipoId;
    }

    public void setTipoId(Long tipoId) {
        this.tipoId = tipoId;
    }
}
