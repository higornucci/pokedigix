package br.com.digix.pokedigix.ataque;

import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRepository;
import br.com.digix.pokedigix.tipo.TipoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(path = { "/api/v1/ataques" }, produces = { "application/json" })
public class AtaqueController {

  private static final Logger logger = LoggerFactory.getLogger(
    AtaqueController.class
  );

  @Autowired
  private AtaqueRepository ataqueRepository;

  @Autowired
  private TipoRepository tipoRepository;

  @Operation(summary = "Buscar um ataque pelo seu id")
  @ApiResponse(
    responseCode = "200",
    description = "Retorna os dados do ataque solicitado"
  )
  @GetMapping(path = "/{id}")
  public ResponseEntity<AtaqueResponseDTO> buscarPorId(@PathVariable Long id) {
    Ataque ataque = ataqueRepository.findById(id).get();

    TipoResponseDTO tipoResponseDTO = new TipoResponseDTO(
      ataque.getTipo().getId(),
      ataque.getTipo().getNome()
    );

    return ResponseEntity.ok(
      new AtaqueResponseDTO(
        ataque.getId(),
        ataque.getForca(),
        ataque.getAcuracia(),
        ataque.getPontosDePoder(),
        ataque.getCategoria(),
        ataque.getNome(),
        ataque.getDescricao(),
        tipoResponseDTO
      )
    );
  }

  @Operation(summary = "Criar um novo Ataque que pode ser usado para Pokemons")
  @ApiResponse(responseCode = "201")
  @PostMapping(consumes = { "application/json" })
  public ResponseEntity<AtaqueResponseDTO> criar(
    @RequestBody AtaqueRequestDTO novoAtaque
  )
    throws AcuraciaInvalidaException, ForcaInvalidaParaCategoriaException, TipoInvalidoParaCategoriaException {
    Tipo tipo = tipoRepository.findById(novoAtaque.getTipoId()).get();
    Ataque ataque = new Ataque(
      novoAtaque.getForca(),
      novoAtaque.getAcuracia(),
      novoAtaque.getPontosDePoder(),
      novoAtaque.getCategoria(),
      novoAtaque.getDescricao(),
      novoAtaque.getNome(),
      tipo
    );
    ataqueRepository.save(ataque);
    TipoResponseDTO tipoDTO = new TipoResponseDTO(tipo.getId(), tipo.getNome());
    return ResponseEntity
      .status(HttpStatus.CREATED)
      .body(
        new AtaqueResponseDTO(
          ataque.getId(),
          ataque.getForca(),
          ataque.getAcuracia(),
          ataque.getPontosDePoder(),
          ataque.getCategoria(),
          ataque.getNome(),
          ataque.getDescricao(),
          tipoDTO
        )
      );
  }
}
