package br.com.digix.pokedigix.ataque;

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
import org.springframework.web.bind.annotation.RestController;

import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRepository;
import br.com.digix.pokedigix.tipo.TipoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = { "/api/v1/ataques" }, produces = { "application/json" })
public class AtaqueController {

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

  @Operation(summary = "Atualizar um Ataque")
	@ApiResponse(responseCode = "200")
	@PutMapping(path = "/{id}", consumes = "application/json")
	public ResponseEntity<AtaqueResponseDTO> atualizarTreinador(@RequestBody AtaqueRequestDTO ataqueRequestDTO,
			@PathVariable Long id) {
		Ataque ataque = ataqueRepository.findById(id).get();
		ataque.setNome(ataqueRequestDTO.getNome());
		ataque.setAcuracia(ataqueRequestDTO.getAcuracia());
		ataque.setCategoria(ataqueRequestDTO.getCategoria());
		ataque.setDescricao(ataqueRequestDTO.getDescricao());
		ataque.setForca(ataqueRequestDTO.getForca());
		ataque.setPontosDePoder(ataqueRequestDTO.getPontosDePoder());

		ataqueRepository.save(ataque);
    TipoResponseDTO tipoDTO = new TipoResponseDTO(ataque.getId(), ataque.getNome());

		return ResponseEntity.ok(new AtaqueResponseDTO(
				ataque.getId(),
				ataque.getForca(),
				ataque.getAcuracia(),
				ataque.getPontosDePoder(),
				ataque.getCategoria(),
        ataque.getNome(),
				ataque.getDescricao(),
        tipoDTO
        ));

	}
  @Operation(summary = "Deletar um Ataque pelo seu id")
  @ApiResponse(responseCode = "204")
  @DeleteMapping(path = "/{id}")
  public ResponseEntity<?> removerAtaqueId(@PathVariable Long id) {
      ataqueRepository.deleteById(id);
      return ResponseEntity.noContent().build();
  }
}
