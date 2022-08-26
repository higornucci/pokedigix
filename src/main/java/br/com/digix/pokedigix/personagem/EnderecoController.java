package br.com.digix.pokedigix.personagem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = { "/api/v1/enderecos" }, produces = { "application/json" })
public class EnderecoController {

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Operation(summary = "Buscar um endereço pelo seu id")
	@ApiResponse(responseCode = "200", description = "Retorna o endereço solicitado")
	@GetMapping(path = "/{id}")
	public ResponseEntity<EnderecoResponseDTO> buscarPorId(@PathVariable Long id) {
		Endereco endereco = enderecoRepository.findById(id).get();
		return ResponseEntity.ok(new EnderecoResponseDTO(
				endereco.getId(),
				endereco.getRegiao(),
				endereco.getCidade()
				));
	}
}
