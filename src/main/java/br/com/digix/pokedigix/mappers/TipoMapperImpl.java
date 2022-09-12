package br.com.digix.pokedigix.mappers;

import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRequestDTO;
import br.com.digix.pokedigix.tipo.TipoResponseDTO;

public class TipoMapperImpl implements TipoMapper {

    @Override
    public Tipo tipoRequestDTOToTipo(TipoRequestDTO tipoRequestDTO) {
        Tipo tipo = new Tipo(tipoRequestDTO.getNome());
        return tipo;
    }

    @Override
    public TipoResponseDTO tipoToTipoResponseDTO(Tipo tipo) {
        return new TipoResponseDTO(tipo.getId(), tipo.getNome());
    }
    
}
