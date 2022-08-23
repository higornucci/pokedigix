package br.com.digix.pokedigix.personagem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = { "api/v1/treinadores" }, produces = { "application/json" })
public class TreinadorController {

	@Autowired
	private TreinadorRepository treinadorRepository;

	@Operation(summary = "Atualizar o Treinador")
	@ApiResponse(responseCode = "204")
	@PutMapping(path = "/{id}", consumes = "application/json")
	public TreinadorResponseDTO atualizarTreinador(@RequestBody TreinadorRequestDTO treinadorRequestDTO,
			@PathVariable Long id) {
		Treinador treinador = treinadorRepository.findById(id).get();
		treinador.setNome(treinadorRequestDTO.getNome());
		treinador.setEndereco(treinadorRequestDTO.getEndereco());
		treinador.setNivel(treinadorRequestDTO.getNivel());
		treinador.setDinheiro(treinadorRequestDTO.getDinheiro());
		treinador.setInsignias(treinadorRequestDTO.getInsignias());

		treinadorRepository.save(treinador);
		
		return new TreinadorResponseDTO(
				treinador.getId(),
				treinador.getNome(),
				treinador.getEndereco(),
				treinador.getNivel(),
				treinador.getDinheiro(),
				treinador.getInsignias());

	}
}
