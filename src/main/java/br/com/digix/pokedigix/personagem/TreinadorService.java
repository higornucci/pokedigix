package br.com.digix.pokedigix.personagem;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.digix.pokedigix.mappers.PokemonMapper;
import br.com.digix.pokedigix.mappers.TreinadorMapper;
import br.com.digix.pokedigix.pokemon.Pokemon;
import br.com.digix.pokedigix.pokemon.PokemonRepository;
import br.com.digix.pokedigix.pokemon.PokemonResponseDTO;

@Service
public class TreinadorService {
    @Autowired
    private TreinadorRepository treinadorRepository;

    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private PokemonMapper pokemonMapper;

    @Autowired
    private TreinadorMapper treinadorMapper;

    public TreinadorResponseDTO buscarPorId(Long id) {
        Treinador treinador = buscarTreinador(id);
        return treinadorMapper.treinadorParaTreinadorResponse(treinador);
    }

    public Collection<PokemonResponseDTO> buscarPorPokemon(Long id) {
        Treinador treinador = buscarTreinador(id);
        return pokemonMapper.pokemonsParaPokemonsResponses(treinador.getPokemons());
    }

    public void removerPorId(Long id) {
        treinadorRepository.deleteById(id);
    }

    public TreinadorResponseDTO atualizar(TreinadorUpdateDTO treinadorUpdateDTO, Long id) {
        Treinador treinador = buscarTreinador(id);
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(treinadorUpdateDTO.getEnderecoId());
        if (enderecoOptional.isEmpty()) {
            throw new NoSuchElementException();
        }
        Endereco endereco = enderecoOptional.get();
        treinador.setNome(treinadorUpdateDTO.getNome());
        treinador.setEndereco(endereco);
        treinador.setNivel(treinadorUpdateDTO.getNivel());
        treinador.setDinheiro(treinadorUpdateDTO.getDinheiro());
        treinador.setInsignias(treinadorUpdateDTO.getInsignias());

        treinadorRepository.save(treinador);

        return treinadorMapper.treinadorParaTreinadorResponse(treinador);
    }

    public TreinadorResponseDTO capturarPokemon(Long idTreinador, Long idPokemon) throws LimiteDePokemonException {
        Treinador treinador = buscarTreinador(idTreinador);
        Optional<Pokemon> pokemonOptional = pokemonRepository.findById(idPokemon);
        if (pokemonOptional.isEmpty()) {
            throw new NoSuchElementException();
        }
        Pokemon pokemon = pokemonOptional.get();
        treinador.capturar(pokemon);
        treinadorRepository.save(treinador);

        return treinadorMapper.treinadorParaTreinadorResponse(treinador);
    }

    public void removerPorNome(String termo){
		treinadorRepository.deleteByNomeContaining(termo);
    }

    public TreinadorResponseDTO cadastrar(TreinadorRequestDTO novoTreinador) throws LimiteDePokemonException{
		Treinador treinador = treinadorRepository.save(treinadorMapper.treinadorRequestParaTreinador(novoTreinador));
        return treinadorMapper.treinadorParaTreinadorResponse(treinador);
    }

    private Treinador buscarTreinador(Long id) {
        Optional<Treinador> treinadorOptional = treinadorRepository.findById(id);
        if (treinadorOptional.isEmpty()) {
            throw new NoSuchElementException();
        }
        return treinadorOptional.get();
    }
}
