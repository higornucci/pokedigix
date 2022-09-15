package br.com.digix.pokedigix.mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.ataque.AtaqueRepository;
import br.com.digix.pokedigix.pokemon.FelicidadeInvalidaException;
import br.com.digix.pokedigix.pokemon.LimiteDeAtaquePokemonException;
import br.com.digix.pokedigix.pokemon.LimiteDeTipoPokemonException;
import br.com.digix.pokedigix.pokemon.NivelPokemonInvalidoException;
import br.com.digix.pokedigix.pokemon.Pokemon;
import br.com.digix.pokedigix.pokemon.PokemonRequestDTO;
import br.com.digix.pokedigix.pokemon.PokemonResponseDTO;
import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRepository;

@Component
public class PokemonMapperImpl implements PokemonMapper {

    @Autowired
    private AtaqueRepository ataqueRepository;

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private AtaqueMapper ataqueMapper;

    @Autowired
    private TipoMapper tipoMapper;

    @Override
    public Pokemon pokemonRequestParaPokemon(PokemonRequestDTO pokemonRequestDTO) throws NivelPokemonInvalidoException,
            FelicidadeInvalidaException, LimiteDeTipoPokemonException, LimiteDeAtaquePokemonException {
        Collection<Tipo> tipos = new ArrayList<>();
        Collection<Ataque> ataques = new ArrayList<>();
        for (Long ataqueId : pokemonRequestDTO.getAtaquesIds()) {
            Optional<Ataque> ataqueOptional = ataqueRepository.findById(ataqueId);
            if (ataqueOptional.isEmpty()) {
                throw new NoSuchElementException();
            }
            Ataque ataque = ataqueOptional.get();
            ataques.add(ataque);
        }
        for (Long tipoId : pokemonRequestDTO.getTiposIds()) {
            Optional<Tipo> tipoOptional = tipoRepository.findById(tipoId);
            if (tipoOptional.isEmpty()) {
                throw new NoSuchElementException();
            }
            Tipo tipo = tipoOptional.get();
            tipos.add(tipo);
        }
        return new Pokemon(
                pokemonRequestDTO.getNome(),
                pokemonRequestDTO.getAltura(),
                pokemonRequestDTO.getPeso(),
                pokemonRequestDTO.getGenero(),
                pokemonRequestDTO.getNivel(),
                pokemonRequestDTO.getNumeroPokedex(),
                pokemonRequestDTO.getFelicidade(),
                tipos,
                ataques);
    }

    @Override
    public PokemonResponseDTO pokemonParaPokemonResponse(Pokemon pokemon) {
        return new PokemonResponseDTO(
                pokemon.getId(),
                pokemon.getNome(),
                pokemon.getAltura(),
                pokemon.getPeso(),
                pokemon.getGenero(),
                pokemon.getNivel(),
                pokemon.getNumeroPokedex(),
                pokemon.getFelicidade(),
                ataqueMapper.ataquesParaAtaquesResponses(pokemon.getAtaques()),
                tipoMapper.tiposParaTiposResponses(pokemon.getTipos()));
    }

    @Override
    public Collection<PokemonResponseDTO> pokemonsParaPokemonsResponses(Collection<Pokemon> pokemons) {
        Collection<PokemonResponseDTO> pokemonsRetornados = new ArrayList<>();
        for (Pokemon pokemon : pokemons) {
            pokemonsRetornados.add(this.pokemonParaPokemonResponse(pokemon));
        }
        return pokemonsRetornados;
    }

}