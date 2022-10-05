package br.com.digix.pokedigix.personagem;

import java.util.Collection;

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

import br.com.digix.pokedigix.pokemon.PokemonResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = { "/api/v1/treinadores" }, produces = { "application/json" })
public class TreinadorController {

	@Autowired
	private TreinadorService treinadorService;

	@Operation(summary = "Buscar um treinador pelo seu id")
	@ApiResponse(responseCode = "200", description = "Retorna o treinador solicitado")
	@GetMapping(path = "/{id}")
	public ResponseEntity<TreinadorResponseDTO> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(treinadorService.buscarPorId(id));
	}

	@Operation(summary = "Buscar pokemons do treinador pelo id do treinador")
	@ApiResponse(responseCode = "200", description = "Retorna uma lista contendo os pokemons do treinador")
	@GetMapping(path = "/{id}/pokemons")
	public ResponseEntity<Collection<PokemonResponseDTO>> buscarPorPokemons(@PathVariable Long id) {
		return ResponseEntity.ok(treinadorService.buscarPorPokemon(id));
	}

	@Operation(summary = "Atualizar o Treinador")
	@ApiResponse(responseCode = "200")
	@PutMapping(path = "/{id}", consumes = "application/json")
	public ResponseEntity<TreinadorResponseDTO> atualizarTreinador(@RequestBody TreinadorUpdateDTO treinadorRequestDTO,
			@PathVariable Long id) {
		return ResponseEntity.ok(treinadorService.atualizar(treinadorRequestDTO, id));
	}

	@Operation(summary = "Treinador capturar um Pokemon")
	@ApiResponse(responseCode = "200")
	@PutMapping(path = "{idTreinador}/pokemons/{idPokemon}/capturar")
	public ResponseEntity<TreinadorResponseDTO> treinadorCapturarPokemon(@PathVariable Long idTreinador,
			@PathVariable Long idPokemon) throws LimiteDePokemonException {

		return ResponseEntity.ok(treinadorService.capturarPokemon(idTreinador, idPokemon));
	}

	@Operation(summary = "Deletar um Treinador pelo seu id")
	@ApiResponse(responseCode = "204")
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<Void> removerTreinadorId(@PathVariable Long id) {
		treinadorService.removerPorId(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Deletar um Treinador pelo seu nome parcial ou completo")
	@ApiResponse(responseCode = "204")
	@DeleteMapping
	@Transactional
	public ResponseEntity<Void> removerTreinador(@RequestParam(required = true) String termo) {
		treinadorService.removerPorNome(termo);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Cadastrar um novo treinador")
	@ApiResponse(responseCode = "201")
	@PostMapping(consumes = { "application/json" })
	public ResponseEntity<TreinadorResponseDTO> cadastrarTreinador(@RequestBody TreinadorRequestDTO novoTreinador)
			throws LimiteDePokemonException {
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(treinadorService.cadastrar(novoTreinador));
	}

	@Operation(summary = "Lista todos os treinadores ")
	@ApiResponse(responseCode = "200")
	@GetMapping
	public ResponseEntity<Collection<TreinadorResponseDTO>> buscarTodos() {
		return ResponseEntity.ok(treinadorService.buscarTodos());
	}
}