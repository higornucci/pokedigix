package br.com.digix.pokedigix.personagem;

import java.util.ArrayList;
import java.util.Collection;
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
import org.webjars.NotFoundException;

import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.ataque.AtaqueResponseDTO;
import br.com.digix.pokedigix.pokemon.Pokemon;
import br.com.digix.pokedigix.pokemon.PokemonRepository;
import br.com.digix.pokedigix.pokemon.PokemonResponseDTO;
import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoResponseDTO;
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

	@Operation(summary = "Buscar um treinador pelo seu id")
	@ApiResponse(responseCode = "200", description = "Retorna o treinador solicitado")
	@GetMapping(path = "/{id}")
	public ResponseEntity<TreinadorResponseDTO> buscarPorId(@PathVariable Long id) {
		Optional<Treinador> treinadorOptional = treinadorRepository.findById(id);
		if (treinadorOptional.isEmpty()) {
			throw new NotFoundException(null);
		}
		Treinador treinador = treinadorOptional.get();
		return ResponseEntity.ok(new TreinadorResponseDTO(
				treinador.getId(), treinador.getEndereco(),
				treinador.getNome(), treinador.getInsignias(),
				treinador.getNivel(),
				treinador.getDinheiro()));
	
	}

	@Operation(summary = "Buscar pokemons do treinador pelo id do treinador")
	@ApiResponse(responseCode = "200", description = "Retorna uma lista contendo os pokemons do treinador")
	@GetMapping(path = "/{id}/pokemons")
	public ResponseEntity<Collection<PokemonResponseDTO>> buscarPorPokemons(@PathVariable Long id) {
		Treinador treinador = new Treinador();
		Optional<Treinador> value  = treinadorRepository.findById(id);
		if(value.isPresent()){ 
			 treinador = value.get();
		}

		Collection<PokemonResponseDTO> pokemonsDTO = new ArrayList<>();
		for (Pokemon pokemon : treinador.getPokemons()) {
			Collection<AtaqueResponseDTO> ataquesDTO = new ArrayList<>();
			for (Ataque ataque : pokemon.getAtaques()) {
				ataquesDTO.add(
						new AtaqueResponseDTO(
								ataque.getId(),
								ataque.getForca(),
								ataque.getAcuracia(),
								ataque.getPontosDePoder(),
								ataque.getCategoria(),
								ataque.getNome(),
								ataque.getDescricao(),
								new TipoResponseDTO(ataque.getTipo().getId(), ataque.getTipo().getNome())));
			}

			Collection<TipoResponseDTO> tiposDTO = new ArrayList<>();
			for (Tipo tipo : pokemon.getTipos()) {
				tiposDTO.add(new TipoResponseDTO(tipo.getId(), tipo.getNome()));
			}

			pokemonsDTO.add(
					new PokemonResponseDTO(
							pokemon.getId(),
							pokemon.getNome(),
							pokemon.getAltura(),
							pokemon.getPeso(),
							pokemon.getGenero(),
							pokemon.getNivel(),
							pokemon.getNumeroPokedex(),
							pokemon.getFelicidade(),
							ataquesDTO,
							tiposDTO));
		}

		return ResponseEntity.ok(pokemonsDTO);
	}

	@Operation(summary = "Atualizar o Treinador")
	@ApiResponse(responseCode = "200")
	@PutMapping(path = "/{id}", consumes = "application/json")
	public ResponseEntity<TreinadorResponseDTO> atualizarTreinador(@RequestBody TreinadorUpdateDTO treinadorRequestDTO,
			@PathVariable Long id) {
		Optional<Treinador> treinadorOptional = treinadorRepository.findById(id);
		if (treinadorOptional.isEmpty()) {
			throw new NotFoundException(null);
		}
		Treinador treinador = treinadorOptional.get();

		Optional<Endereco> enderecoOptional = enderecoRepository.findById(treinadorRequestDTO.getEnderecoId());
		if (enderecoOptional.isEmpty()) {
			throw new NotFoundException(null);
		}
		Endereco endereco = enderecoOptional.get();
		treinador.setNome(treinadorRequestDTO.getNome());
		treinador.setEndereco(endereco);
		treinador.setNivel(treinadorRequestDTO.getNivel());
		treinador.setDinheiro(treinadorRequestDTO.getDinheiro());
		treinador.setInsignias(treinadorRequestDTO.getInsignias());

		treinadorRepository.save(treinador);

		return ResponseEntity.ok(
				new TreinadorResponseDTO(
						treinador.getId(),
						treinador.getEndereco(),
						treinador.getNome(),
						treinador.getInsignias(),
						treinador.getNivel(),
						treinador.getDinheiro()));
	}

	@Operation(summary = "Treinador capturar um Pokemon")
	@ApiResponse(responseCode = "200")
	@PutMapping(path = "{idTreinador}/pokemons/{idPokemon}/capturar")
	public ResponseEntity<TreinadorResponseDTO> treinadorCapturarPokemon(@PathVariable Long idTreinador,
			@PathVariable Long idPokemon) throws LimiteDePokemonException {
		Optional<Treinador> treinadorOptional = treinadorRepository.findById(idTreinador);
		if (treinadorOptional.isEmpty()) {
			throw new NotFoundException(null);
		}
		Treinador treinador = treinadorOptional.get();

		Optional<Pokemon> pokemonOptional = pokemonRepository.findById(idPokemon);
		if (pokemonOptional.isEmpty()) {
			throw new NotFoundException(null);
		}
		Pokemon pokemon = pokemonOptional.get();
		treinador.capturar(pokemon);
		treinadorRepository.save(treinador);
		return ResponseEntity.ok(new TreinadorResponseDTO(treinador.getId(), treinador.getEndereco(),
				treinador.getNome(), treinador.getInsignias(), treinador.getNivel(), treinador.getDinheiro()));
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
		Optional<Endereco> enderecoOptional = enderecoRepository.findById(novoTreinador.getIdEndereco());
		if (enderecoOptional.isEmpty()) {
			throw new NotFoundException(null);
		}
		Endereco endereco = enderecoOptional.get();

		Optional<Pokemon> pokemoOptional = pokemonRepository.findById(novoTreinador.getIdPrimeiroPokemon());
		if (pokemoOptional.isEmpty()) {
			throw new NotFoundException(null);
		}
		Pokemon pokemon = pokemoOptional.get();
		Treinador treinador = new Treinador(novoTreinador.getNome(), endereco, pokemon);
		treinadorRepository.save(treinador);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new TreinadorResponseDTO(treinador.getId(), treinador.getEndereco(), treinador.getNome(),
						treinador.getInsignias(), treinador.getDinheiro(), treinador.getNivel()));
	}

}
