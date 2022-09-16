package br.com.digix.pokedigix.tipo;

import java.util.Collection;

import javax.naming.NameNotFoundException;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = { "/api/v1/tipos" }, produces = { "application/json" })
public class TipoController {

  @Autowired
  private TipoService tipoService;

  @Operation(summary = "Criar um novo tipo que pode ser usado para Pokemons ou Ataques")
  @ApiResponse(responseCode = "201")
  @PostMapping(consumes = { "application/json" })
  public ResponseEntity<TipoResponseDTO> criarTipo(@RequestBody TipoRequestDTO novoTipo) {
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(tipoService.criar(novoTipo));
  }

  @Operation(summary = "Buscar todos os tipos sem ordem")
  @ApiResponse(responseCode = "200", description = "Lista de tipos cadastrados")
  @GetMapping
  public ResponseEntity<Collection<TipoResponseDTO>> buscarTodos(
      @RequestParam(required = false, name = "termo") String nome) {
    return ResponseEntity.ok(tipoService.buscarTodos(nome));
  }

  @Operation(summary = "Buscar um Tipo pelo seu id")
  @ApiResponse(responseCode = "200")
  @GetMapping(path = "/{id}")
  public ResponseEntity<TipoResponseDTO> buscarPorId(@PathVariable Long id) throws NameNotFoundException {
    return ResponseEntity.ok(tipoService.buscarPorId(id));
  }

  @Operation(summary = "Deletar um Tipo pelo seu id")
  @ApiResponse(responseCode = "204")
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> removerTipoPorId(@PathVariable Long id) {
    tipoService.removerPorId(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Deletar um Tipo pelo seu nome parcial ou completo")
  @ApiResponse(responseCode = "204")
  @DeleteMapping
  @Transactional
  public ResponseEntity<Void> removerTipoPorNome(@RequestParam(required = true) String termo) {
    tipoService.removerPeloNomeParcial(termo);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Atualizar o Tipo")
  @ApiResponse(responseCode = "200", description = "Retorna os dados atualizados")
  @PutMapping(path = "/{id}", consumes = "application/json")
  public ResponseEntity<TipoResponseDTO> alterarTipo(
      @RequestBody TipoRequestDTO tipoRequestDTO,
      @PathVariable Long id) {
    return ResponseEntity.ok(tipoService.alterar(tipoRequestDTO, id));
  }
}