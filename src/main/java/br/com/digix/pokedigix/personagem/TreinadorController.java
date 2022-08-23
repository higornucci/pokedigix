package br.com.digix.pokedigix.personagem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.digix.pokedigix.pokemon.Pokemon;
import br.com.digix.pokedigix.pokemon.PokemonRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = { "/api/v1/treinadores" }, produces = { "application/json" })
public class TreinadorController {
    @Autowired
    private TreinadorRepository treinadorRepository;
    
    @Autowired
    private PokemonRepository pokemonRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Operation(summary = "Cadastrar um novo treinador")
    @ApiResponse(responseCode = "201")
    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<TreinadorResponseDTO> cadastrarTreinador(@RequestBody TreinadorRequestDTO novoTreinador) throws LimiteDePokemonException {
        Endereco endereco = enderecoRepository.findById(novoTreinador.getIdEndereco()).get();
        Pokemon pokemon = pokemonRepository.findById(novoTreinador.getIdPrimeiroPokemon()).get();
        Treinador treinador = new Treinador(novoTreinador.getNome(), endereco, pokemon);
        treinadorRepository.save(treinador);
        return ResponseEntity.status(HttpStatus.CREATED).body(new TreinadorResponseDTO(treinador.getId(), treinador.getEndereco(), treinador.getNome(), treinador.getInsignias(), treinador.getDinheiro(), treinador.getNivel()));
    }

}
