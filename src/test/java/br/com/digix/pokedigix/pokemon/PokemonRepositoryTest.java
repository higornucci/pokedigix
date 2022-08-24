package br.com.digix.pokedigix.pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.ataque.AtaqueBuilder;
import br.com.digix.pokedigix.tipo.Tipo;
import br.com.digix.pokedigix.tipo.TipoRepository;

@DataJpaTest
public class PokemonRepositoryTest {

    @Autowired
    private PokemonRepository pokemonRepository;
    
    @Autowired
    private TipoRepository tipoRepository;

    @Test
    public void deve_salvar_um_pokemon() throws Exception {
        Pokemon gastly = new PokemonBuilder().construir();

        pokemonRepository.save(gastly);

        assertNotNull(gastly.getId());
    }

    @Test
    public void deve_salvar_um_pokemon_com_um_tipo() throws Exception {
        int quantidadeDeTiposEsperada = 1;
        Tipo fantasma = new Tipo("Fantasma");
        Pokemon gastly = new PokemonBuilder().comTipo(fantasma).construir();
        pokemonRepository.save(gastly);

        Pokemon gastlyRetornado = pokemonRepository.findById(gastly.getId()).get();

        assertNotNull(gastlyRetornado.getTipos());
        assertEquals(quantidadeDeTiposEsperada,
                gastlyRetornado.getTipos().size());
        assertTrue(gastlyRetornado.getTipos().contains(fantasma));
    }

    @Test
    public void deve_salvar_um_pokemon_com_ataque() throws Exception {
        Ataque ataque = new AtaqueBuilder().construir();
        Pokemon pokemon = new PokemonBuilder().comAtaque(ataque).construir();

        pokemonRepository.save(pokemon);

        assertTrue(pokemon.getAtaques().contains(ataque));
    }  

    @Test
    public void deve_buscar_um_pokemon_pelo_seu_tipo() throws NivelPokemonInvalidoException, FelicidadeInvalidaException, LimiteDeTipoPokemonException  {
    
        Tipo tipo = new Tipo("Psíquico");
        Pokemon pokemon = new PokemonBuilder().comTipo(tipo).construir();
        tipoRepository.save(tipo);
        
        pokemonRepository.save(pokemon);       

        Collection<Pokemon> pokemonsRetornados = pokemonRepository.buscarPorTipo(tipo.getId());
        assertTrue(pokemonsRetornados.contains(pokemon));
    }

    @Test
    public void deve_buscar_um_pokemon_por_nome() throws Exception {
        Pokemon pokemon = new PokemonBuilder().comNome("Gastly").construir();
        pokemonRepository.save(pokemon);

        Collection<Pokemon> pokemonRetornado = pokemonRepository.findByNomeContaining("Gastly");


        assertTrue(pokemonRetornado.contains(pokemon));
    }
    @Test
    public void pode_nao_ter_genero() throws Exception { 
        Genero genero = null;
        Pokemon pokemon = new PokemonBuilder().comGenero(genero).construir();
        pokemonRepository.save(pokemon);

        Collection<Pokemon> pokemonRetornado = pokemonRepository.findByNomeContaining("Gastly");


        assertTrue(pokemonRetornado.contains(pokemon));
    }


   
}
