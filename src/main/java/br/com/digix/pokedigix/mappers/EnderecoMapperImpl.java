package br.com.digix.pokedigix.mappers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.stereotype.Component;

import br.com.digix.pokedigix.personagem.Endereco;
import br.com.digix.pokedigix.personagem.EnderecoRequestDTO;
import br.com.digix.pokedigix.personagem.EnderecoResponseDTO;

@Component
public class EnderecoMapperImpl implements EnderecoMapper {

	@Override
	public Endereco enderecoRequestParaEndereco(EnderecoRequestDTO enderecoRequestDTO) {
		return new Endereco(enderecoRequestDTO.getRegiao(), enderecoRequestDTO.getCidade());
	}

	@Override
	public EnderecoResponseDTO enderecoParaEnderecoResponseDTO(Endereco endereco) {
		return new EnderecoResponseDTO(endereco.getId(), endereco.getRegiao(), endereco.getCidade());
	}

	@Override
	public Collection<EnderecoResponseDTO> enderecosParaEnderecoResponseDTOs(Collection<Endereco> enderecos) {
		Collection<EnderecoResponseDTO> enderecoResponseDTOs = new ArrayList<>();
		for (Endereco endereco : enderecos) {
			enderecoResponseDTOs.add(enderecoParaEnderecoResponseDTO(endereco));
		}
		return enderecoResponseDTOs;
	}
}
