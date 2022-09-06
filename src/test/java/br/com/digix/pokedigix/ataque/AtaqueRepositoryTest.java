package br.com.digix.pokedigix.ataque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRepository;

@DataJpaTest
public class AtaqueRepositoryTest {
    
    @Autowired
    private AtaqueRepository ataqueRepository;

    @Autowired
    private TipoRepository tipoRepository;

    @Test
    public void deve_salvar_um_ataque() throws AcuraciaInvalidaException, ForcaInvalidaParaCategoriaException, TipoInvalidoParaCategoriaException {
        int forca = 40;
        int acuracia = 100;
        int pontosDePoder = 35;
        Categoria categoria = Categoria.FISICO;
        String nome = "Ataque Rapido";
        String descricao = "O usuario ataque antes do oponente.";
        Tipo tipo = new Tipo("Normal");
        Ataque ataque = new Ataque(forca, acuracia, pontosDePoder, categoria, nome, descricao, tipo);

        ataqueRepository.save(ataque);

        assertNotNull(ataque.getId());
    }

    @Test
     void deve_salvar_um_tipo_para_um_ataque() throws AcuraciaInvalidaException, ForcaInvalidaParaCategoriaException, TipoInvalidoParaCategoriaException {
        int forca = 40;
        int acuracia = 100;
        int pontosDePoder = 35;
        Categoria categoria = Categoria.FISICO;
        String nome = "Ataque Rapido";
        String descricao = "O usuario ataque antes do oponente.";
        Tipo tipo = new Tipo("Normal");
        Ataque ataque = new Ataque(forca, acuracia, pontosDePoder, categoria, nome, descricao, tipo);
        tipoRepository.save(tipo);
        ataqueRepository.save(ataque);

        Ataque ataqueRetornado = ataqueRepository.findById(ataque.getId()).get();

        assertEquals(tipo.getNome(), ataqueRetornado.getTipo().getNome());
        assertNotNull(ataqueRetornado.getTipo().getId());
    }

    @Test
     void deve_um_ataque_pelo_seu_tipo() throws AcuraciaInvalidaException, ForcaInvalidaParaCategoriaException, TipoInvalidoParaCategoriaException {
        int forca = 40;
        int acuracia = 100;
        int pontosDePoder = 35;
        Categoria categoria = Categoria.FISICO;
        String nome = "Ataque Rapido";
        String descricao = "O usuario ataque antes do oponente.";
        Tipo tipo = new Tipo("Normal");
        Ataque ataque = new Ataque(forca, acuracia, pontosDePoder, categoria, nome, descricao, tipo);
        tipoRepository.save(tipo);
        ataqueRepository.save(ataque);

        Collection<Ataque> ataquesRetornados = ataqueRepository.findByTipo(tipo);

        assertTrue(ataquesRetornados.contains(ataque));
    }
}
