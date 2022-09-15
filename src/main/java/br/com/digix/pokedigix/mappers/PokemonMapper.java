package br.com.digix.pokedigix.mappers;

import java.util.Collection;

import org.mapstruct.Mapper;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import br.com.digix.pokedigix.pokemon.FelicidadeInvalidaException;
import br.com.digix.pokedigix.pokemon.LimiteDeAtaquePokemonException;
import br.com.digix.pokedigix.pokemon.LimiteDeTipoPokemonException;
import br.com.digix.pokedigix.pokemon.NivelPokemonInvalidoException;
import br.com.digix.pokedigix.pokemon.Pokemon;
import br.com.digix.pokedigix.pokemon.PokemonRequestDTO;
import br.com.digix.pokedigix.pokemon.PokemonResponseDTO;

@Mapper(componentModel = "spring")
public interface PokemonMapper {
    public Pokemon pokemonRequestParaPokemon(PokemonRequestDTO pokemonRequestDTO) throws NivelPokemonInvalidoException, FelicidadeInvalidaException, LimiteDeTipoPokemonException, LimiteDeAtaquePokemonException, NotFoundException;
    public PokemonResponseDTO pokemonParaPokemonResponseDTO(Pokemon pokemon);
    public Collection<PokemonResponseDTO>  pokemonsParaPokemonsResponseDTO (Collection<Pokemon> pokemons);
}
