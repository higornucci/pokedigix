package br.com.digix.pokedigix.personagem;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(
  path = { "/api/v1/enderecos" },
  produces = { "application/json" }
)
public class EnderecoController {

  @Autowired
  private EnderecoRepository enderecoRepository;

  @Operation(summary = "Cadastrar um novo endereço")
  @ApiResponse(responseCode = "201")
  @PostMapping(consumes = { "application/json" })
  public ResponseEntity<EnderecoResponseDTO> cadastrarEndereco(
    @RequestBody EnderecoRequestDTO novoEndereco
  ) {
    Endereco endereco = new Endereco(
      novoEndereco.getCidade(),
      novoEndereco.getRegiao()
    );
    enderecoRepository.save(endereco);
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(
        new EnderecoResponseDTO(
          endereco.getId(),
          endereco.getCidade(),
          endereco.getRegiao()
        )
      );
  }

  @Operation(summary = "Buscar um endereço pelo seu id")
  @ApiResponse(
    responseCode = "200",
    description = "Retorna o endereço solicitado"
  )
  @GetMapping(path = "/{id}")
  public ResponseEntity<EnderecoResponseDTO> buscarPorId(
    @PathVariable Long id
  ) {
    Endereco endereco = enderecoRepository.findById(id).get();
    return ResponseEntity.ok(
      new EnderecoResponseDTO(
        endereco.getId(),
        endereco.getRegiao(),
        endereco.getCidade()
      )
    );
  }
}
