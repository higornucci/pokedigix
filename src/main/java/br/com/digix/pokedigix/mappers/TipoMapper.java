package br.com.digix.pokedigix.mappers;

import java.util.Collection;
import java.util.List;

import org.mapstruct.Mapper;

import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRequestDTO;
import br.com.digix.pokedigix.tipo.TipoResponseDTO;
import br.com.digix.pokedigix.tipo.TipoResponsePageDTO;

@Mapper(componentModel = "spring")
public interface TipoMapper {
    public Tipo tipoRequestParaTipo(TipoRequestDTO tipoRequestDTO);

    public TipoResponseDTO tipoParaTipoResponse(Tipo tipo);
    public Collection<TipoResponseDTO> tiposParaTiposResponses(Collection<Tipo> tipos);

    public TipoResponsePageDTO tiposParaTiposResponsesPaginadoOrdenado(List<Tipo> content, int totalPages);
}
