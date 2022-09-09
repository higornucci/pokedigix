package br.com.digix.pokedigix.personagem;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Test;

import br.com.digix.pokedigix.pokemon.FelicidadeInvalidaException;
import br.com.digix.pokedigix.pokemon.LimiteDeAtaquePokemonException;
import br.com.digix.pokedigix.pokemon.LimiteDeTipoPokemonException;
import br.com.digix.pokedigix.pokemon.NivelPokemonInvalidoException;
import br.com.digix.pokedigix.pokemon.Pokemon;
import br.com.digix.pokedigix.pokemon.PokemonBuilder;

public class LiderTest {
    
    @Test
    public void deve_poder_criar_um_lider() throws NivelPokemonInvalidoException, FelicidadeInvalidaException, LimiteDeTipoPokemonException, LimiteDeAtaquePokemonException {
        // Arrage
        String nome = "Thor";
        Endereco endereco = new EnderecoBuilder().construir();
        Collection<Pokemon> pokemons = new ArrayList<>();
        pokemons.add(new PokemonBuilder().construir());
        Insignia insignia = Insignia.TROVAO;


        Lider lider = new Lider(nome, endereco, pokemons, insignia);
       
        assertEquals(nome, lider.getNome());
        assertEquals(endereco, lider.getEndereco());
        assertEquals(pokemons, lider.getPokemons());
        assertEquals(insignia, lider.getInsignia());
       
        
    }

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
