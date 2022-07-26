package br.com.digix.pokedigix.pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.ataque.AtaqueBuilder;
import br.com.digix.pokedigix.tipo.Tipo;

@DataJpaTest
public class PokemonRepositoryTest {

    @Autowired
    private PokemonRepository pokemonRepository;

    @Test
    public void deve_salvar_um_pokemon() {
        Pokemon gastly = new PokemonBuilder().construir();

        pokemonRepository.save(gastly);

        assertNotNull(gastly.getId());
    }
    
    @Test
    public void deve_salvar_um_pokemon_com_um_tipo() {
        int quantidadeDeTiposEsperada = 1;
        Tipo fantasma = new Tipo("Fantasma");
        Pokemon gastly = new PokemonBuilder().comTipo(fantasma).construir();
        pokemonRepository.save(gastly);
        

        Pokemon gastlyRetornado = 
        pokemonRepository.findById(gastly.getId()).get();

        assertNotNull(gastlyRetornado.getTipos());
        assertEquals(quantidadeDeTiposEsperada, 
            gastlyRetornado.getTipos().size());
        assertTrue(gastlyRetornado.getTipos().contains(fantasma));
    }
    
    @Test
    public void deve_salvar_um_pokemon_com_ataque() {
        Ataque ataque = new AtaqueBuilder().construir(); 
        Pokemon pokemon = new PokemonBuilder().comAtaque(ataque).construir();
        
        pokemonRepository.save(pokemon);

        assertTrue(pokemon.getAtaques().contains(ataque));
    }  
}
