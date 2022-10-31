package br.com.digix.pokedigix.mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRequestDTO;
import br.com.digix.pokedigix.tipo.TipoResponseDTO;
import br.com.digix.pokedigix.tipo.TipoResponsePageDTO;

@Component
public class TipoMapperImpl implements TipoMapper {

    @Override
    public Tipo tipoRequestParaTipo(TipoRequestDTO tipoRequestDTO) {
        return new Tipo(tipoRequestDTO.getNome());
    }

    @Override
    public TipoResponseDTO tipoParaTipoResponse(Tipo tipo) {
        return new TipoResponseDTO(tipo.getId(), tipo.getNome());
    }

    @Override
    public Collection<TipoResponseDTO> tiposParaTiposResponses(Collection<Tipo> tipos) {
        Collection<TipoResponseDTO> tiposDTOs = new ArrayList<>();
        for (Tipo tipo : tipos) {
            tiposDTOs.add(this.tipoParaTipoResponse(tipo));
        }
        return tiposDTOs;
    }

    @Override
    public TipoResponsePageDTO tiposParaTiposResponsesPaginadoOrdenado(List<Tipo> tipos, int quantidadeDePaginas) {
        return new TipoResponsePageDTO(this.tiposParaTiposResponses(tipos), quantidadeDePaginas);
    }
    
}
