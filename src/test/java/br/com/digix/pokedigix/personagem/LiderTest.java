package br.com.digix.pokedigix.personagem;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import br.com.digix.pokedigix.pokemon.FelicidadeInvalidaException;
import br.com.digix.pokedigix.pokemon.LimiteDeAtaquePokemonException;
import br.com.digix.pokedigix.pokemon.LimiteDeTipoPokemonException;
import br.com.digix.pokedigix.pokemon.NivelPokemonInvalidoException;

public class LiderTest {
    
    @Test
    public void deve_ter_insignia() throws NivelPokemonInvalidoException, FelicidadeInvalidaException, LimiteDeTipoPokemonException, LimiteDeAtaquePokemonException {
        // Arrage
        int insigniaEsperada = 1;
        Insignia insignia = Insignia.ARCO_IRIS;
        
        // Action
        Lider lider = new LiderBuilder().comInsignia(insignia).construir();
        Collection<Insignia> listaDeInsignias = new ArrayList<>();
        listaDeInsignias.add(lider.getInsignia());

        // Assert
        assertThat(insigniaEsperada).isEqualTo(listaDeInsignias.size());
    }
}
