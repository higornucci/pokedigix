package br.com.digix.pokedigix.mappers;

import org.mapstruct.Mapper;

import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRequestDTO;
import br.com.digix.pokedigix.tipo.TipoResponseDTO;

@Mapper
public interface TipoMapper {    
    public Tipo tipoRequestDTOToTipo(TipoRequestDTO tipoRequestDTO);
    public TipoResponseDTO tipoToTipoResponseDTO(Tipo tipo);
}
