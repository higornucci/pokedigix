package br.com.digix.pokedigix.tipo;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = { "/api/v1/tipos" }, produces = { "application/json" })
public class TipoController {

    @Autowired
    private TipoRepository tipoRepository;

    @PostMapping(consumes = { "application/json" })
    public TipoResponseDTO criarTipo(@RequestBody TipoRequestDTO novoTipo) {
        Tipo tipo = new Tipo(novoTipo.getNome());
        tipoRepository.save(tipo);
        return new TipoResponseDTO(tipo.getId(), tipo.getNome());
    }

    @GetMapping
    public Collection<TipoResponseDTO> buscarTodos(
            @RequestParam(required = false, name = "termo") String nome) {
        Iterable<Tipo> tipos;
        if (nome != null) {
            tipos = tipoRepository.findByNomeContaining(nome);
        } else {
            tipos = tipoRepository.findAll();
        }
        Collection<TipoResponseDTO> tiposRetornados = new ArrayList<>();
        for (Tipo tipo : tipos) {
            tiposRetornados.add(new TipoResponseDTO(tipo.getId(), tipo.getNome()));
        }
        return tiposRetornados;
    }

    @GetMapping(path = "/{id}")
    public TipoResponseDTO buscarPorId(@PathVariable Long id) {
        Tipo tipo = tipoRepository.findById(id).get();
        return new TipoResponseDTO(tipo.getId(), tipo.getNome());
    }

    @DeleteMapping(path = "/{id}")
    public void removerTipoPorId(@PathVariable Long id) {
        tipoRepository.deleteById(id);
    }

    @DeleteMapping
    @Transactional
    public void removerTipoPorNome(@RequestParam(required = true) String termo) {
        tipoRepository.deleteByNomeContaining(termo);
    }

    @PutMapping(path = "/{id}", consumes = "application/json")
    public TipoResponseDTO alterarTipo(@RequestBody TipoRequestDTO tipoRequestDTO,
            @PathVariable Long id) {
        Tipo tipoParaAlterar = tipoRepository.findById(id).get();
        tipoParaAlterar.setNome(tipoRequestDTO.getNome());
        tipoRepository.save(tipoParaAlterar);
        return new TipoResponseDTO(tipoParaAlterar.getId(), tipoParaAlterar.getNome());
    }

}
