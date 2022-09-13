package br.com.digix.pokedigix.personagem;

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

import br.com.digix.pokedigix.mappers.EnderecoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(path = { "/api/v1/enderecos" }, produces = { "application/json" })
public class EnderecoController {
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoMapper enderecoMapper;

    @Operation(summary = "Deletar um Endereço pelo seu id")
    @ApiResponse(responseCode = "204")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> removerEnderecoId(@PathVariable Long id) {
        enderecoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Cadastrar um novo endereço")
    @ApiResponse(responseCode = "201")
    @PostMapping(consumes = { "application/json" })
    public ResponseEntity<EnderecoResponseDTO> cadastrarEndereco(@RequestBody EnderecoRequestDTO novoEndereco) {
        Endereco endereco = enderecoMapper.enderecoRequestParaEndereco(novoEndereco);
        enderecoRepository.save(endereco);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(enderecoMapper.enderecoParaEnderecoResponseDTO(endereco));
    }

    @Operation(summary = "Buscar um endereço pelo seu id")
    @ApiResponse(responseCode = "200", description = "Retorna o endereço solicitado")
    @GetMapping(path = "/{id}")
    public ResponseEntity<EnderecoResponseDTO> buscarPorId(@PathVariable Long id) {
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);
        if (enderecoOptional.isEmpty()) {
            throw new NotFoundException(null);
        }
        Endereco endereco = enderecoOptional.get();
        return ResponseEntity.ok(enderecoMapper.enderecoParaEnderecoResponseDTO(endereco));
    }

    @Operation(summary = "Buscar um endereço pelo nome da cidade")
    @ApiResponse(responseCode = "200", description = "Retorna o endereço solicitado")
    @GetMapping(path = "/cidade")
    public ResponseEntity<Collection<EnderecoResponseDTO>> buscarPorCidade(
            @RequestParam(required = false, name = "termo") String cidade) {
        Collection<Endereco> enderecos;
        if (cidade == null || cidade.isEmpty()) {
            enderecos = (Collection<Endereco>) enderecoRepository.findAll();
        } else {
            enderecos = enderecoRepository.findByCidadeContaining(cidade);
        }

        return ResponseEntity.ok(enderecoMapper.enderecosParaEnderecoResponseDTOs(enderecos));
    }

    @Operation(summary = "Buscar um endereço pelo nome da região")
    @ApiResponse(responseCode = "200", description = "Retorna o endereço solicitado")
    @GetMapping(path = "/regiao")
    public ResponseEntity<Collection<EnderecoResponseDTO>> buscarPorRegiao(
            @RequestParam(required = false, name = "termo") String regiao) {
        Collection<Endereco> enderecos;
        if (regiao == null || regiao.isEmpty()) {
            enderecos = (Collection<Endereco>) enderecoRepository.findAll();
        } else {
            enderecos = enderecoRepository.findByRegiaoContaining(regiao);
        }

        return ResponseEntity.ok(enderecoMapper.enderecosParaEnderecoResponseDTOs(enderecos));
    }

    @Operation(summary = "Atualizar o Endereço")
    @ApiResponse(responseCode = "200")
    @PutMapping(path = "/{id}", consumes = "application/json")
    public ResponseEntity<EnderecoResponseDTO> atualizarEndereco(@RequestBody EnderecoUpdateDTO enderecoRequestDTO,
            @PathVariable Long id) {
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);
        if (enderecoOptional.isEmpty()) {
            throw new NotFoundException(null);
        }
        Endereco endereco = enderecoOptional.get();
        endereco.setRegiao(enderecoRequestDTO.getRegiao());
        endereco.setCidade(enderecoRequestDTO.getCidade());
        enderecoRepository.save(endereco);

        return ResponseEntity.ok(enderecoMapper.enderecoParaEnderecoResponseDTO(endereco));
    }
}
