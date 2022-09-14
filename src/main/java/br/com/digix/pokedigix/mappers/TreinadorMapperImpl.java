package br.com.digix.pokedigix.mappers;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.digix.pokedigix.personagem.Endereco;
import br.com.digix.pokedigix.personagem.EnderecoRepository;
import br.com.digix.pokedigix.personagem.LimiteDePokemonException;
import br.com.digix.pokedigix.personagem.Treinador;
import br.com.digix.pokedigix.personagem.TreinadorRequestDTO;
import br.com.digix.pokedigix.personagem.TreinadorResponseDTO;
import br.com.digix.pokedigix.pokemon.Pokemon;
import br.com.digix.pokedigix.pokemon.PokemonRepository;

@Component
public class TreinadorMapperImpl implements TreinadorMapper {
    @Autowired
    PokemonMapper pokemonMapper;

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Override
    public Treinador treinadorRequestParaTreinador(TreinadorRequestDTO treinadorRequestDTO) throws LimiteDePokemonException {
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(treinadorRequestDTO.getIdEndereco());
        if (enderecoOptional.isEmpty()) {
            throw new NoSuchElementException();
        }
        Endereco endereco = enderecoOptional.get();

        Optional<Pokemon> pokemoOptional = pokemonRepository.findById(treinadorRequestDTO.getIdPrimeiroPokemon());
        if (pokemoOptional.isEmpty()) {
            throw new NoSuchElementException();
        }
        Pokemon pokemon = pokemoOptional.get();
        return new Treinador(treinadorRequestDTO.getNome(), endereco, pokemon);
    }

    @Override
    public TreinadorResponseDTO treinadorParaTreinadorResponseDTO(Treinador treinador) {
        return new TreinadorResponseDTO(treinador.getId(), treinador.getEndereco(), treinador.getNome(),
        treinador.getInsignias(), treinador.getDinheiro(), treinador.getNivel());
    }

}
