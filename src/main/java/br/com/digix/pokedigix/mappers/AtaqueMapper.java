package br.com.digix.pokedigix.mappers;

import java.util.Collection;

import org.mapstruct.Mapper;

import br.com.digix.pokedigix.ataque.AcuraciaInvalidaException;
import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.ataque.AtaqueRequestDTO;
import br.com.digix.pokedigix.ataque.AtaqueResponseDTO;
import br.com.digix.pokedigix.ataque.ForcaInvalidaParaCategoriaException;
import br.com.digix.pokedigix.ataque.TipoInvalidoParaCategoriaException;

@Mapper(componentModel = "spring")
public interface AtaqueMapper {
    public Ataque ataqueRequestParaAtaque(AtaqueRequestDTO ataqueRequestDTO) throws AcuraciaInvalidaException, ForcaInvalidaParaCategoriaException, TipoInvalidoParaCategoriaException;
    public AtaqueResponseDTO ataqueParaAtaqueResponseDTO(Ataque ataque);
    public Collection<AtaqueResponseDTO> ataqueParaAtaqueResponseDTO(Collection<Ataque> ataques);
}
