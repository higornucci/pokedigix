package br.com.digix.pokedigix.tipo;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class TipoResponsePageDTO {
    private Collection<TipoResponseDTO> tipos;
    private int totalPaginas;
}
