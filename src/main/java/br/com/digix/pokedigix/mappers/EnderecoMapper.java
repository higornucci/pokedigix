package br.com.digix.pokedigix.mappers;

import java.util.Collection;

import org.mapstruct.Mapper;

import br.com.digix.pokedigix.personagem.Endereco;
import br.com.digix.pokedigix.personagem.EnderecoRequestDTO;
import br.com.digix.pokedigix.personagem.EnderecoResponseDTO;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {
    public Endereco enderecoRequestParaEndereco(EnderecoRequestDTO enderecoRequestDTO);

    public EnderecoResponseDTO enderecoParaEnderecoResponseDTO(Endereco endereco);

    public Collection<EnderecoResponseDTO> enderecosParaEnderecoResponseDTOs(Collection<Endereco> enderecos);
}
