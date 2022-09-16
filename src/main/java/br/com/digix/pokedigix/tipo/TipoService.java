package br.com.digix.pokedigix.tipo;

import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.digix.pokedigix.mappers.TipoMapper;

@Service
public class TipoService {

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private TipoMapper tipoMapper;

    public TipoResponseDTO criar(TipoRequestDTO tipoRequestDTO) {
        Tipo tipo = tipoMapper.tipoRequestParaTipo(tipoRequestDTO);
        tipoRepository.save(tipo);
        return tipoMapper.tipoParaTipoResponse(tipo);
    }

    public Collection<TipoResponseDTO> buscarTodos(String nome) {
        Collection<Tipo> tipos;
        if (nome != null) {
            tipos = tipoRepository.findByNomeContaining(nome);
        } else {
            tipos = (Collection<Tipo>) tipoRepository.findAll();
        }
        return tipoMapper.tiposParaTiposResponses(tipos);
    }

    public TipoResponseDTO buscarPorId(Long id) {
        return tipoMapper.tipoParaTipoResponse(buscarTipoPeloId(id));
    }

    public void removerPorId(Long id) {
        tipoRepository.deleteById(id);
    }

    public void removerPeloNomeParcial(String nome) {
        tipoRepository.deleteByNomeContaining(nome);
    }

    public TipoResponseDTO alterar(TipoRequestDTO tipoRequestDTO, Long id) {
        Tipo tipoParaAlterar = buscarTipoPeloId(id);
        tipoParaAlterar.setNome(tipoRequestDTO.getNome());
        tipoRepository.save(tipoParaAlterar);
        return tipoMapper.tipoParaTipoResponse(tipoParaAlterar);
    }

    private Tipo buscarTipoPeloId(Long id) {
        Optional<Tipo> tipoOptional = tipoRepository.findById(id);
        if (tipoOptional.isEmpty()) {
          throw new NoSuchElementException();
        }
        return tipoOptional.get();
    }
}
