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
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PokemonRepository pokemonRepository;

    @Override
    public TreinadorResponseDTO treinadorParaTreinadorResponse(Treinador treinador) {
        return new TreinadorResponseDTO(
						treinador.getId(),
						treinador.getEndereco(),
						treinador.getNome(),
						treinador.getInsignias(),
						treinador.getNivel(),
						treinador.getDinheiro());
    }

    @Override
    public Treinador treinadorRequestParaTreinador(TreinadorRequestDTO treinadorRequestDTO) throws LimiteDePokemonException {
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(treinadorRequestDTO.getIdEndereco());
		if (enderecoOptional.isEmpty()) {
			throw new NoSuchElementException();
		}
		Optional<Pokemon> pokemoOptional = pokemonRepository.findById(treinadorRequestDTO.getIdPrimeiroPokemon());
		if (pokemoOptional.isEmpty()) {
			throw new NoSuchElementException();
		}
		return new Treinador(treinadorRequestDTO.getNome(), enderecoOptional.get(), pokemoOptional.get());
    }
    
}
