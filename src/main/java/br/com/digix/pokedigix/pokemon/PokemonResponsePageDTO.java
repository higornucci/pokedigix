package br.com.digix.pokedigix.pokemon;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class PokemonResponsePageDTO {
    private Collection<PokemonResponseDTO> pokemons;
    private int totalPaginas;
}
