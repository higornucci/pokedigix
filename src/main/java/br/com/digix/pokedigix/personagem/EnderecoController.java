package br.com.digix.pokedigix.personagem;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = { "/api/v1/enderecos" }, produces = { "application/json" })
public class EnderecoController {
    @Autowired
    private EnderecoService enderecoService;

    @Operation(summary = "Deletar um Endereço pelo seu id")
    @ApiResponse(responseCode = "204")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> removerEnderecoId(@PathVariable Long id) {
        enderecoService.removerPorId(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cadastrar um novo endereço")
    @ApiResponse(responseCode = "201")
    @PostMapping(consumes = { "application/json" })
    public ResponseEntity<EnderecoResponseDTO> cadastrarEndereco(
            @RequestBody EnderecoRequestDTO novoEndereco) {
        return ResponseEntity.status(HttpStatus.CREATED).body(enderecoService.criar(novoEndereco));
    }

    @Operation(summary = "Buscar um endereço pelo seu id")
    @ApiResponse(responseCode = "200", description = "Retorna o endereço solicitado")
    @GetMapping(path = "/{id}")
    public ResponseEntity<EnderecoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(enderecoService.buscarPorId(id));
    }

    @Operation(summary = "Buscar um endereço pelo nome da cidade")
    @ApiResponse(responseCode = "200", description = "Retorna o endereço solicitado")
    @GetMapping(path = "/cidade")
    public ResponseEntity<Collection<EnderecoResponseDTO>> buscarPorCidade(
            @RequestParam(required = false, name = "termo") String cidade) {
        return ResponseEntity.ok(enderecoService.buscarPorNome(cidade));
    }

    @Operation(summary = "Buscar um endereço pelo nome da região")
    @ApiResponse(responseCode = "200", description = "Retorna o endereço solicitado")
    @GetMapping(path = "/regiao")
    public ResponseEntity<Collection<EnderecoResponseDTO>> buscarPorRegiao(
            @RequestParam(required = false, name = "termo") String regiao) {
        return ResponseEntity.ok(enderecoService.buscarPorRegiao(regiao));
    }

    @Operation(summary = "Atualizar o Endereço")
    @ApiResponse(responseCode = "200")
    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<EnderecoResponseDTO> atualizarEndereco(@RequestBody EnderecoRequestDTO enderecoRequestDTO,
            @PathVariable Long id) {

        return ResponseEntity.ok(enderecoService.alterar(enderecoRequestDTO, id));
    }

    @Operation(summary = "Lista todos os endereços")
	@ApiResponse(responseCode = "200")
	@GetMapping
	public ResponseEntity<Collection<EnderecoResponseDTO>> buscarTodos() {
		return ResponseEntity.ok(enderecoService.buscarTodos());
	}
}