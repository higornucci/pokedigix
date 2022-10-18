package br.com.digix.pokedigix.ataque;


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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = { "/api/v1/ataques" }, produces = { "application/json" })
public class AtaqueController {

  @Autowired
  private AtaqueService ataqueService;

  @Operation(summary = "Buscar um ataque pelo seu id")
  @ApiResponse(responseCode = "200", description = "Retorna os dados do ataque solicitado")
  @GetMapping(path = "/{id}")
  public ResponseEntity<AtaqueResponseDTO> buscarPorId(@PathVariable Long id) {
    return ResponseEntity.ok(ataqueService.buscarPorId(id));
  }

  @Operation(summary = "Criar um novo Ataque que pode ser usado para Pokemons")
  @ApiResponse(responseCode = "201")
  @PostMapping(consumes = { "application/json" })
  public ResponseEntity<AtaqueResponseDTO> criarAtaque(@RequestBody AtaqueRequestDTO novoAtaque)
      throws AcuraciaInvalidaException, ForcaInvalidaParaCategoriaException, TipoInvalidoParaCategoriaException {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(ataqueService.criar(novoAtaque));
  }

  @Operation(summary = "Atualizar um Ataque")
  @ApiResponse(responseCode = "200")
  @PutMapping(path = "/{id}", consumes = "application/json")

  public ResponseEntity<AtaqueResponseDTO> atualizarTreinador(@RequestBody AtaqueRequestDTO ataqueRequestDTO,
      @PathVariable Long id) {
    return ResponseEntity.ok(ataqueService.alterar(ataqueRequestDTO, id));
  }

  @Operation(summary = "Deletar um Ataque pelo seu id")
  @ApiResponse(responseCode = "204")
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> removerAtaqueId(@PathVariable Long id) {
    ataqueService.removerPorId(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Lista todos os ataques recebendo seu nome ou parcial")
  @ApiResponse(responseCode = "200")
  @GetMapping
  public ResponseEntity<AtaqueResponsePageDTO> buscar(
      @RequestParam(required = false, name = "pagina", defaultValue = "0") int pagina,
      @RequestParam(required = false, name = "tamanho", defaultValue = "5") int tamanho,
      @RequestParam(required = false, name = "campoOrdenacao", defaultValue = "nome") String campoOrdenacao,
      @RequestParam(required = false, name = "direcao", defaultValue = "ASC") String direcao,
      @RequestParam(required = false, name = "termo") String nome) {

    return ResponseEntity.ok(ataqueService.buscar(pagina, tamanho, campoOrdenacao, direcao, nome));
  }
}