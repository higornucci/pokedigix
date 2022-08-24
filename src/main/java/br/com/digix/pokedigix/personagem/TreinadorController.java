package br.com.digix.pokedigix.personagem;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.digix.pokedigix.pokemon.Pokemon;
import br.com.digix.pokedigix.pokemon.PokemonRepository;
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

	@Operation(summary = "Atualizar o Treinador")
	@ApiResponse(responseCode = "200")
	@PutMapping(path = "/{id}", consumes = "application/json")
	public ResponseEntity<TreinadorResponseDTO> atualizarTreinador(@RequestBody TreinadorUpdateDTO treinadorRequestDTO,
			@PathVariable Long id) {
		Treinador treinador = treinadorRepository.findById(id).get();
		Endereco endereco = enderecoRepository.findById(treinadorRequestDTO.getEnderecoId()).get();
		treinador.setNome(treinadorRequestDTO.getNome());
		treinador.setEndereco(endereco);
		treinador.setNivel(treinadorRequestDTO.getNivel());
		treinador.setDinheiro(treinadorRequestDTO.getDinheiro());
		treinador.setInsignias(treinadorRequestDTO.getInsignias());

		treinadorRepository.save(treinador);

		return ResponseEntity.ok(new TreinadorResponseDTO(
				treinador.getId(),
				treinador.getEndereco(),
				treinador.getNome(),
				treinador.getInsignias(),
				treinador.getNivel(),
				treinador.getDinheiro()));

	}

	@Operation(summary = "Deletar um Treinador pelo seu id")
	@ApiResponse(responseCode = "204")
	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> removerTreinadorId(@PathVariable Long id) {
		treinadorRepository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Deletar um Treinador pelo seu nome parcial ou completo")
	@ApiResponse(responseCode = "204")
	@DeleteMapping
	@Transactional
	public ResponseEntity<?> removerTreinador(@RequestParam(required = true) String termo) {
		treinadorRepository.deleteByNomeContaining(termo);
		return ResponseEntity.noContent().build();
	}

	@Operation(summary = "Cadastrar um novo treinador")
    @ApiResponse(responseCode = "201")
    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<TreinadorResponseDTO> cadastrarTreinador(@RequestBody TreinadorRequestDTO novoTreinador) throws LimiteDePokemonException {
        Endereco endereco = enderecoRepository.findById(novoTreinador.getIdEndereco()).get();
        Pokemon pokemon = pokemonRepository.findById(novoTreinador.getIdPrimeiroPokemon()).get();
        Treinador treinador = new Treinador(novoTreinador.getNome(), endereco, pokemon);
        treinadorRepository.save(treinador);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TreinadorResponseDTO(treinador.getId(), treinador.getEndereco(), treinador.getNome(), treinador.getInsignias(), treinador.getDinheiro(), treinador.getNivel()));
    }

}
