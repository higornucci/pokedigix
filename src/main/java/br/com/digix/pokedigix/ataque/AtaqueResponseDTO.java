package br.com.digix.pokedigix.ataque;

import br.com.digix.pokedigix.tipo.TipoResponseDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AtaqueResponseDTO extends AtaqueDTO {

    private Long id;
    private TipoResponseDTO tipo;

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
}
