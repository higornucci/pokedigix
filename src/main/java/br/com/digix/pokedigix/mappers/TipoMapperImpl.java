package br.com.digix.pokedigix.mappers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRequestDTO;
import br.com.digix.pokedigix.tipo.TipoResponseDTO;

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
    public Collection<TipoResponseDTO> tiposParaTipoResponseDTOs(Collection<Tipo> tipos) {
        Collection<TipoResponseDTO> tiposDTOs = new ArrayList<>();
        for (Tipo tipo : tipos) {
            tiposDTOs.add(tipoParaTipoResponse(tipo));
        }

        return tiposDTOs;
    }

}
