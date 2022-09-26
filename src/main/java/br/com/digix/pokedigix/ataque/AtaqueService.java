package br.com.digix.pokedigix.ataque;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.digix.pokedigix.mappers.AtaqueMapper;

@Service
public class AtaqueService {
	@Autowired
	private AtaqueRepository ataqueRepository;

	@Autowired
	private AtaqueMapper ataqueMapper;

	public AtaqueResponseDTO buscarPorId(Long id) {
		return ataqueMapper.ataqueParaAtaqueResponseDTO(buscarAtaquePeloId(id));
	}

	private Ataque buscarAtaquePeloId(Long id) {
		Optional<Ataque> ataqueOptional = ataqueRepository.findById(id);
		if (ataqueOptional.isEmpty()) {
			throw new NoSuchElementException();
		}
		return ataqueOptional.get();
	}

	public AtaqueResponseDTO criar(AtaqueRequestDTO ataqueRequestDTO)
			throws AcuraciaInvalidaException, ForcaInvalidaParaCategoriaException, TipoInvalidoParaCategoriaException {
		Ataque ataque = ataqueMapper.ataqueRequestParaAtaque(ataqueRequestDTO);
		ataqueRepository.save(ataque);
		return ataqueMapper.ataqueParaAtaqueResponseDTO(ataque);
	}

	public AtaqueResponseDTO alterar(AtaqueRequestDTO ataqueRequestDTO, Long id) {
		Ataque ataqueParaAlterar = buscarAtaquePeloId(id);
		ataqueParaAlterar.setNome(ataqueRequestDTO.getNome());
		ataqueRepository.save(ataqueParaAlterar);
		return ataqueMapper.ataqueParaAtaqueResponseDTO(ataqueParaAlterar);
	}

	public void removerPorId(Long id) {
		ataqueRepository.deleteById(id);
	}
}
