package br.com.digix.pokedigix.personagem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

    @RestController
@RequestMapping(path = { "/api/v1/treinadores" }, produces = { "application/json" })
public class TreinadorController {

    private static final Logger logger = LoggerFactory.getLogger(TreinadorController.class);

    @Autowired
    private TreinadorRepository treinadorRepository;

   

    @Operation(summary = "Buscar um treinador pelo seu id")
    @ApiResponse(responseCode = "200", description = "Retorna o treinador solicitado")
    @GetMapping(path = "/{id}")
    public ResponseEntity<TreinadorResponseDTO> buscarPorId(@PathVariable Long id) {
        Treinador treinador = treinadorRepository.findById(id).get();

       
        
        return ResponseEntity.ok(new TreinadorResponseDTO(treinador.getId(),treinador.getNome(), treinador.getEndereco(), 
        treinador.getDinheiro(), treinador.getNivel(), treinador.getInsignias()));
    }
    
}

