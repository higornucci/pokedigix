package br.com.digix.pokedigix.ataque;
import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class AtaqueResponsePageDTO {
    private Collection<AtaqueResponseDTO> pokemons;
    private int totalPaginas;

}
