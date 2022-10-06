package br.com.digix.pokedigix.mappers;

import java.util.Collection;

import org.mapstruct.Mapper;

import br.com.digix.pokedigix.personagem.LimiteDePokemonException;
import br.com.digix.pokedigix.personagem.Treinador;
import br.com.digix.pokedigix.personagem.TreinadorRequestDTO;
import br.com.digix.pokedigix.personagem.TreinadorResponseDTO;

@Mapper(componentModel = "spring")
public interface TreinadorMapper {
    public TreinadorResponseDTO treinadorParaTreinadorResponse(Treinador treinador);
    public Treinador treinadorRequestParaTreinador(TreinadorRequestDTO treinadorRequestDTO) throws LimiteDePokemonException;
    public Collection<TreinadorResponseDTO> treinadoresParaTreinadoresResponse(Collection<Treinador> findAll);
}
