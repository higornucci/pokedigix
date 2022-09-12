package br.com.digix.pokedigix.mappers;

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
    
}
