package br.com.digix.pokedigix.mappers;

import org.mapstruct.Mapper;

import br.com.digix.pokedigix.personagem.Treinador;
import br.com.digix.pokedigix.personagem.TreinadorResponseDTO;

@Mapper(componentModel = "spring")
public interface TreinadorMapper {
    
    public TreinadorResponseDTO treinadoParaTreinadorResponseDTO(Treinador treinador);

    
}
