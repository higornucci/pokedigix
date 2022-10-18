package br.com.digix.pokedigix.ataque;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.digix.pokedigix.mappers.AtaqueMapper;
import br.com.digix.pokedigix.tipo.TipoRepository;

@Service
public class AtaqueService {
    @Autowired
    private AtaqueRepository ataqueRepository;

    @Autowired
    private AtaqueMapper ataqueMapper;

    @Autowired
    private TipoRepository tipoRepository;

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
        ataqueParaAlterar.setAcuracia(ataqueRequestDTO.getAcuracia());
        ataqueParaAlterar.setCategoria(ataqueRequestDTO.getCategoria());
        ataqueParaAlterar.setDescricao(ataqueRequestDTO.getDescricao());
        ataqueParaAlterar.setForca(ataqueRequestDTO.getForca());
        ataqueParaAlterar.setPontosDePoder(ataqueRequestDTO.getPontosDePoder());
        ataqueParaAlterar.setTipo(tipoRepository.findById(ataqueRequestDTO.getTipoId()).get());

        ataqueRepository.save(ataqueParaAlterar);

        return ataqueMapper.ataqueParaAtaqueResponseDTO(ataqueParaAlterar);
    }

    public void removerPorId(Long id) {
        ataqueRepository.deleteById(id);
    }

    public AtaqueResponsePageDTO buscar(int pagina, int tamanho, String campoOrdenacao, String direcao,
            String nome) {
        Pageable pageable = criarPaginaOrdenada(pagina, tamanho, campoOrdenacao, direcao);
        return mapearResposta(nome, pageable);
    }

    private AtaqueResponsePageDTO mapearResposta(String nome, Pageable pageable) {
        Page<Ataque> ataques;
        if (nome != null && !nome.isEmpty()) {
            ataques = ataqueRepository.findByNomeContaining(nome, pageable);
        } else {
            ataques = ataqueRepository.findAll(pageable);
        }
        return ataqueMapper.ataquesParaAtaquesResponsesPaginadoOrdenado(ataques.getContent(), ataques.getTotalPages());
    }

    private Pageable criarPaginaOrdenada(int pagina, int tamanho, String campoOrdenacao, String direcao) {
        if (direcao.equals("ASC"))
            return PageRequest.of(pagina, tamanho, Sort.by(campoOrdenacao).ascending());
        else
            return PageRequest.of(pagina, tamanho, Sort.by(campoOrdenacao).descending());
    }

}
