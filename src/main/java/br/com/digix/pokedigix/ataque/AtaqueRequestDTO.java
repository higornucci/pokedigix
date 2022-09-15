package br.com.digix.pokedigix.ataque;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AtaqueRequestDTO extends AtaqueDTO {

    private Long tipoId;

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

}