package br.com.digix.pokedigix.mappers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.digix.pokedigix.ataque.AcuraciaInvalidaException;
import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.ataque.AtaqueRequestDTO;
import br.com.digix.pokedigix.ataque.AtaqueResponseDTO;
import br.com.digix.pokedigix.ataque.Categoria;
import br.com.digix.pokedigix.ataque.ForcaInvalidaParaCategoriaException;
import br.com.digix.pokedigix.ataque.TipoInvalidoParaCategoriaException;
import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRepository;

@Component
public class AtaqueMapperImpl implements AtaqueMapper {

    @Autowired
    private TipoMapper tipoMapper;

    @Autowired
    private TipoRepository tipoRepository;

    @Override
    public Ataque ataqueRequestParaAtaque(AtaqueRequestDTO ataqueRequestDTO)
            throws AcuraciaInvalidaException, ForcaInvalidaParaCategoriaException, TipoInvalidoParaCategoriaException {
        Optional<Tipo> tipoOptional = tipoRepository.findById(ataqueRequestDTO.getTipoId());
        if (tipoOptional.isEmpty()) {
            throw new NoSuchElementException();
        }
        Tipo tipo = tipoOptional.get();
        if (ataqueRequestDTO.getCategoria().equals(Categoria.EFEITO)) {
            return new Ataque(ataqueRequestDTO.getAcuracia(), ataqueRequestDTO.getPontosDePoder(), ataqueRequestDTO.getCategoria(),
                    ataqueRequestDTO.getNome(), ataqueRequestDTO.getDescricao(), tipo);
        } else {
            return new Ataque(ataqueRequestDTO.getForca(), ataqueRequestDTO.getAcuracia(),
                    ataqueRequestDTO.getPontosDePoder(), ataqueRequestDTO.getCategoria(),
                    ataqueRequestDTO.getNome(),
                    ataqueRequestDTO.getDescricao(), tipo);
        }
    }

    @Override
    public AtaqueResponseDTO ataqueParaAtaqueResponseDTO(Ataque ataque) {
        return new AtaqueResponseDTO(
                ataque.getId(),
                ataque.getForca(),
                ataque.getAcuracia(),
                ataque.getPontosDePoder(),
                ataque.getCategoria(),
                ataque.getNome(),
                ataque.getDescricao(),
                tipoMapper.tipoParaTipoResponse(ataque.getTipo()));
    }

    @Override
    public Collection<AtaqueResponseDTO> ataquesParaAtaquesResponses(
            Collection<Ataque> ataques) {
        Collection<AtaqueResponseDTO> ataquesDTOs = new ArrayList<>();
        for (Ataque ataque : ataques) {
            ataquesDTOs.add(this.ataqueParaAtaqueResponseDTO(ataque));
        }
        return ataquesDTOs;
    }

}