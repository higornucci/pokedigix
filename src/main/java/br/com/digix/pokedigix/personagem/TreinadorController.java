package br.com.digix.pokedigix.personagem;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.digix.pokedigix.mappers.PokemonMapper;
import br.com.digix.pokedigix.mappers.TreinadorMapper;
import br.com.digix.pokedigix.pokemon.Pokemon;
import br.com.digix.pokedigix.pokemon.PokemonRepository;
import br.com.digix.pokedigix.pokemon.PokemonResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = { "/api/v1/treinadores" }, produces = { "application/json" })
public class TreinadorController {

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

	@Operation(summary = "Buscar um treinador pelo seu id")
	@ApiResponse(responseCode = "200", description = "Retorna o treinador solicitado")
	@GetMapping(path = "/{id}")
	public ResponseEntity<TreinadorResponseDTO> buscarPorId(@PathVariable Long id) {

		Treinador treinador = buscarTreinador(id);
		return ResponseEntity.ok(treinadorMapper.treinadorParaTreinadorResponse(treinador));

	}

	@Operation(summary = "Buscar pokemons do treinador pelo id do treinador")
	@ApiResponse(responseCode = "200", description = "Retorna uma lista contendo os pokemons do treinador")
	@GetMapping(path = "/{id}/pokemons")
	public ResponseEntity<Collection<PokemonResponseDTO>> buscarPorPokemons(@PathVariable Long id) {

		Treinador treinador = buscarTreinador(id);
		return ResponseEntity.ok(pokemonMapper.pokemonsParaPokemonsResponses(treinador.getPokemons()));
	}

	@Operation(summary = "Atualizar o Treinador")
	@ApiResponse(responseCode = "200")
	@PutMapping(path = "/{id}", consumes = "application/json")
	public ResponseEntity<TreinadorResponseDTO> atualizarTreinador(@RequestBody TreinadorUpdateDTO treinadorRequestDTO,
			@PathVariable Long id) {
		Treinador treinador = buscarTreinador(id);
		Optional<Endereco> enderecoOptional = enderecoRepository.findById(treinadorRequestDTO.getEnderecoId());
		if (enderecoOptional.isEmpty()) {
			throw new NoSuchElementException();
		}
		Endereco endereco = enderecoOptional.get();
		treinador.setNome(treinadorRequestDTO.getNome());
		treinador.setEndereco(endereco);
		treinador.setNivel(treinadorRequestDTO.getNivel());
		treinador.setDinheiro(treinadorRequestDTO.getDinheiro());
		treinador.setInsignias(treinadorRequestDTO.getInsignias());

		treinadorRepository.save(treinador);

		return ResponseEntity.ok(treinadorMapper.treinadorParaTreinadorResponse(treinador));
	}

	@Operation(summary = "Treinador capturar um Pokemon")
	@ApiResponse(responseCode = "200")
	@PutMapping(path = "{idTreinador}/pokemons/{idPokemon}/capturar")
	public ResponseEntity<TreinadorResponseDTO> treinadorCapturarPokemon(@PathVariable Long idTreinador,
			@PathVariable Long idPokemon) throws LimiteDePokemonException {
		Treinador treinador = buscarTreinador(idTreinador);
		Optional<Pokemon> pokemonOptional = pokemonRepository.findById(idPokemon);
		if (pokemonOptional.isEmpty()) {
			throw new NoSuchElementException();
		}
		Pokemon pokemon = pokemonOptional.get();
		treinador.capturar(pokemon);
		treinadorRepository.save(treinador);
		return ResponseEntity.ok(treinadorMapper.treinadorParaTreinadorResponse(treinador));
	}

	@Operation(summary = "Deletar um Treinador pelo seu id")
	@ApiResponse(responseCode = "204")
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> removerTreinadorId(@PathVariable Long id) {
		treinadorRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Deletar um Treinador pelo seu nome parcial ou completo")
	@ApiResponse(responseCode = "204")
	@DeleteMapping
	@Transactional
	public ResponseEntity<Void> removerTreinador(@RequestParam(required = true) String termo) {
		treinadorRepository.deleteByNomeContaining(termo);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Cadastrar um novo treinador")
	@ApiResponse(responseCode = "201")
	@PostMapping(consumes = { "application/json" })
	public ResponseEntity<TreinadorResponseDTO> cadastrarTreinador(@RequestBody TreinadorRequestDTO novoTreinador)
			throws LimiteDePokemonException {
		Treinador treinador = treinadorRepository.save(treinadorMapper.treinadorRequestParaTreinador(novoTreinador));
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(treinadorMapper.treinadorParaTreinadorResponse(treinador));
	}

	private Treinador buscarTreinador(Long id) {
		Optional<Treinador> treinadorOptional = treinadorRepository.findById(id);
		if (treinadorOptional.isEmpty()) {
			throw new NoSuchElementException();
		}
		Treinador treinador = treinadorOptional.get();
		return treinador;
	}
}