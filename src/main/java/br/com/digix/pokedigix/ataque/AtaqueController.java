package br.com.digix.pokedigix.ataque;

import java.util.Collection;
import java.util.Optional;

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

import br.com.digix.pokedigix.mappers.AtaqueMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = { "/api/v1/ataques" }, produces = { "application/json" })
public class AtaqueController {

  @Autowired
  private AtaqueRepository ataqueRepository;

  @Autowired
  private AtaqueMapper ataqueMapper;

  @Operation(summary = "Buscar um ataque pelo seu id")
  @ApiResponse(responseCode = "200", description = "Retorna os dados do ataque solicitado")
  @GetMapping(path = "/{id}")
  public ResponseEntity<AtaqueResponseDTO> buscarPorId(@PathVariable Long id) {
    Optional<Ataque> ataqueOptional = ataqueRepository.findById(id);
    if (ataqueOptional.isEmpty()) {
      throw new NotFoundException(null);
    }
    Ataque ataque = ataqueOptional.get();
    return ResponseEntity.ok(ataqueMapper.ataqueParaAtaqueResponseDTO(ataque));
  }

  @Operation(summary = "Criar um novo Ataque que pode ser usado para Pokemons")
  @ApiResponse(responseCode = "201")
  @PostMapping(consumes = { "application/json" })
  public ResponseEntity<AtaqueResponseDTO> criar(
      @RequestBody AtaqueRequestDTO novoAtaque)
      throws AcuraciaInvalidaException, ForcaInvalidaParaCategoriaException, TipoInvalidoParaCategoriaException {
    Ataque ataque = ataqueMapper.ataqueRequestParaAtaque(novoAtaque);
    ataqueRepository.save(ataque);
    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(ataqueMapper.ataqueParaAtaqueResponseDTO(ataque));
  }

  @Operation(summary = "Atualizar um Ataque")
  @ApiResponse(responseCode = "200")
  @PutMapping(path = "/{id}", consumes = "application/json")

  public ResponseEntity<AtaqueResponseDTO> atualizarTreinador(@RequestBody AtaqueRequestDTO ataqueRequestDTO,
      @PathVariable Long id) {
    Optional<Ataque> ataqueOptional = ataqueRepository.findById(id);
    if (ataqueOptional.isEmpty()) {
      throw new NotFoundException(null);
    }
    Ataque ataque = ataqueOptional.get();
    ataque.setNome(ataqueRequestDTO.getNome());
    ataque.setAcuracia(ataqueRequestDTO.getAcuracia());
    ataque.setCategoria(ataqueRequestDTO.getCategoria());
    ataque.setDescricao(ataqueRequestDTO.getDescricao());
    ataque.setForca(ataqueRequestDTO.getForca());
    ataque.setPontosDePoder(ataqueRequestDTO.getPontosDePoder());
    ataqueRepository.save(ataque);

    return ResponseEntity.ok(ataqueMapper.ataqueParaAtaqueResponseDTO(ataque));

  }

  @Operation(summary = "Deletar um Ataque pelo seu id")
  @ApiResponse(responseCode = "204")
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<Void> removerAtaqueId(@PathVariable Long id) {
    ataqueRepository.deleteById(id);
    return ResponseEntity.noContent().build();
  }

  @Operation(summary = "Lista todos os ataques recebendo seu nome ou parcial")
  @ApiResponse(responseCode = "200")
  @GetMapping
  public ResponseEntity<Collection<AtaqueResponseDTO>> buscarPelonome(
    @RequestParam(required = false, name = "termo") String nome){
  Collection<Ataque> ataques;
  if(nome != null){
    ataques = ataqueRepository.findByNomeContaining(nome);
  }else {
    ataques = (Collection<Ataque>) ataqueRepository.findAll();
  }
  return ResponseEntity.ok(ataqueMapper.ataquesParaAtaquesResponses(ataques));
  }
}