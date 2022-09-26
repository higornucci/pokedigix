package br.com.digix.pokedigix.personagem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.digix.pokedigix.mappers.EnderecoMapper;

@Service
public class EnderecoService {
    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private EnderecoMapper enderecoMapper;

    public void removerPorId(Long id) {
        enderecoRepository.deleteById(id);
    }

    public EnderecoResponseDTO criar(EnderecoRequestDTO EnderecoRequestDTO) {
        Endereco endereco = enderecoMapper.enderecoRequestParaEndereco(EnderecoRequestDTO);
        enderecoRepository.save(endereco);
        return enderecoMapper.enderecoParaEnderecoResponseDTO(endereco);
    }

    public EnderecoResponseDTO buscarPorId(Long id) {
        Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);
        if (enderecoOptional.isEmpty()) {
            throw new NoSuchElementException();
        }
        Endereco endereco = enderecoOptional.get();
        return enderecoMapper.enderecoParaEnderecoResponseDTO(endereco);
    }

    public Collection<EnderecoResponseDTO> buscarPorNome(String nome) {
        Collection<Endereco> enderecos;
        if (nome == null || nome.isEmpty()) {
            enderecos = (Collection<Endereco>) enderecoRepository.findAll();
        } else {
            enderecos = enderecoRepository.findByCidadeContaining(nome);
        }

        Collection<EnderecoResponseDTO> enderecosRetornados = new ArrayList<>();

        for (Endereco endereco : enderecos) {
            enderecosRetornados
                    .add(new EnderecoResponseDTO(endereco.getId(), endereco.getRegiao(),
                            endereco.getCidade()));
        }
        return enderecoMapper.enderecosParaEnderecoResponseDTOs(enderecos);
    }

    public Collection<EnderecoResponseDTO> buscarPorRegiao(String regiao) {
        Collection<Endereco> enderecos;
        if (regiao == null || regiao.isEmpty()) {
            enderecos = (Collection<Endereco>) enderecoRepository.findAll();
        } else {
            enderecos = enderecoRepository.findByRegiaoContaining(regiao);
        }
        return enderecoMapper.enderecosParaEnderecoResponseDTOs(enderecos);
    }

    public EnderecoResponseDTO alterar(EnderecoRequestDTO enderecoRequestDTO, Long id) {

        Optional<Endereco> enderecoOptional = enderecoRepository.findById(id);
        if (enderecoOptional.isEmpty()) {
            throw new NoSuchElementException();
        }
        Endereco endereco = enderecoOptional.get();
        endereco.setRegiao(enderecoRequestDTO.getRegiao());
        endereco.setCidade(enderecoRequestDTO.getCidade());
        enderecoRepository.save(endereco);

        return enderecoMapper.enderecoParaEnderecoResponseDTO(endereco);
    }

}
