package br.com.digix.pokedigix.pokemon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.webjars.NotFoundException;
import org.springframework.stereotype.Service;

import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.ataque.AtaqueRepository;
import br.com.digix.pokedigix.mappers.PokemonMapper;
import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRepository;

@Service
public class PokemonService {
    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private AtaqueRepository ataqueRepository;

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private PokemonMapper pokemonMapper;

    public void removerPorId(Long id) {
        pokemonRepository.deleteById(id);
    }

    public void removerPeloNomeParcial(String nome) {
        pokemonRepository.deleteByNomeContaining(nome);
    }

    public PokemonResponseDTO criar(PokemonRequestDTO pokemonRequestDTO) throws NivelPokemonInvalidoException,
            FelicidadeInvalidaException, LimiteDeTipoPokemonException, LimiteDeAtaquePokemonException {
        Pokemon pokemon = pokemonMapper.pokemonRequestParaPokemon(pokemonRequestDTO);
        pokemonRepository.save(pokemon);
        return pokemonMapper.pokemonParaPokemonResponse(pokemon);
    }

    public Collection<PokemonResponseDTO> buscarPokemonPeloIdDoTipo(Long id) {
        Collection<Pokemon> pokemons = pokemonRepository.buscarPorTipo(id);
        return pokemonMapper.pokemonsParaPokemonsResponses(pokemons);
    }

    public PokemonResponseDTO atualizar(PokemonRequestDTO pokemonRequestDTO, long id)
            throws LimiteDeAtaquePokemonException, LimiteDeTipoPokemonException {
        Collection<Tipo> tipos = new ArrayList<>();
        Collection<Ataque> ataques = new ArrayList<>();

        for (Long ataqueId : pokemonRequestDTO.getAtaquesIds()) {
            Optional<Ataque> ataqueOptional = ataqueRepository.findById(ataqueId);
            if (ataqueOptional.isEmpty()) {
                throw new NotFoundException(null);
            }
            Ataque ataque = ataqueOptional.get();
            ataques.add(ataque);
        }

        for (Long tipoId : pokemonRequestDTO.getTiposIds()) {
            Optional<Tipo> tipoOptional = tipoRepository.findById(tipoId);
            if (tipoOptional.isEmpty()) {
                throw new NotFoundException(null);
            }
            Tipo tipo = tipoOptional.get();
            tipos.add(tipo);
        }
        Optional<Pokemon> pokemonOptional = pokemonRepository.findById(id);
        if (pokemonOptional.isEmpty()) {
            throw new NotFoundException(null);
        }

        Pokemon alterarPokemon = pokemonOptional.get();
        alterarPokemon.setNome(pokemonRequestDTO.getNome());
        alterarPokemon.setAltura(pokemonRequestDTO.getAltura());
        alterarPokemon.setPeso(pokemonRequestDTO.getPeso());
        alterarPokemon.setGenero(pokemonRequestDTO.getGenero());
        alterarPokemon.setNivel(pokemonRequestDTO.getNivel());
        alterarPokemon.setNumeroPokedex(pokemonRequestDTO.getNumeroPokedex());
        alterarPokemon.setFelicidade(pokemonRequestDTO.getFelicidade());
        alterarPokemon.setAtaques(ataques);
        alterarPokemon.setTipos(tipos);
        pokemonRepository.save(alterarPokemon);

        return pokemonMapper.pokemonParaPokemonResponse(alterarPokemon);

    }

    public Collection<PokemonResponseDTO> buscarPeloNome(int pagina, int tamanho, String campoOrdenacao, String direcao, String nome) {
        Collection<Pokemon> pokemons;
        Pageable pageable = null;
        if(direcao.equals("ASC"))
             pageable = PageRequest.of(pagina, tamanho, Sort.by(campoOrdenacao).ascending());
        else
            pageable = PageRequest.of(pagina, tamanho, Sort.by(campoOrdenacao).descending());

        if (nome != null) {
            pokemons = pokemonRepository.findByNomeContaining(nome, pageable).getContent();
        } else {
            pokemons = (Collection<Pokemon>) pokemonRepository.findAll(pageable).getContent();
        }
        return pokemonMapper.pokemonsParaPokemonsResponses(pokemons);
    }

}
