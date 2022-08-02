package br.com.digix.pokedigix.pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.ataque.AtaqueBuilder;
import br.com.digix.pokedigix.tipo.Tipo;

public class PokemonTest {
    @Test
    public void deve_criar_um_pokemon() throws Exception {
        String nome = "Gastly";
        double altura = 0.8;
        double peso = 0.1;
        Genero genero = Genero.MASCULINO;
        int nivel = 5;
        int numeroPokedex = 92;
        int felicidade = 0;
        List<Tipo> tipos = new ArrayList<>();
        tipos.add(new Tipo("Fantasma"));
        List<Ataque> ataques = new ArrayList<>();
        ataques.add(new AtaqueBuilder().construir());

        Pokemon gastly = new Pokemon(nome, 
            altura, 
            peso, 
            genero, 
            nivel, 
            numeroPokedex, 
            felicidade, 
            tipos,
            ataques);

        assertEquals(nome, gastly.getNome());
        assertEquals(altura, gastly.getAltura());
        assertEquals(peso, gastly.getPeso());
        assertEquals(genero, gastly.getGenero());
        assertEquals(nivel, gastly.getNivel());
        assertEquals(numeroPokedex, gastly.getNumeroPokedex());
        assertEquals(felicidade, gastly.getFelicidade());
    }

    @Test
    public void deve_cadastrar_um_tipo_para_o_pokemon() throws NivelPokemonInvalidoException {
        Tipo tipo = new Tipo("Fantasma");

        Pokemon gastly = new PokemonBuilder().comTipo(tipo).construir();

        assertTrue(gastly.getTipos().contains(tipo));        
    }

    @Test
    public void deve_ter_nivel_minimo_um() throws NivelPokemonInvalidoException {
        int nivelMinimo = 1;
        
        Pokemon pokemon = new PokemonBuilder().comNivel(nivelMinimo).construir();

        assertEquals(nivelMinimo, pokemon.getNivel());
    }

    @Test
    public void deve_ter_nivel_maximo_cem() throws NivelPokemonInvalidoException {
        int nivelMaximo = 100;

        Pokemon pokemon = new PokemonBuilder().comNivel(nivelMaximo).construir();

        assertEquals(nivelMaximo, pokemon.getNivel());
    }

    @Test
    public void nao_pode_ter_nivel_menor_que_um() {
        int nivelInvalido = 0;

        assertThrows(NivelPokemonInvalidoException.class, () -> {
            new PokemonBuilder().comNivel(nivelInvalido).construir();
        });
    } 

    @Test
    public void nao_pode_ter_nivel_maior_que_cem() {
        int nivelInvalido = 101;

        assertThrows(NivelPokemonInvalidoException.class, () -> {
            new PokemonBuilder().comNivel(nivelInvalido).construir();
        });
    } 

    @Test
    public void deve_ter_felicidade_minima_zero() throws NivelPokemonInvalidoException {
        int felicidadeMinima = 0;

        Pokemon pokemon = new PokemonBuilder().comFelicidade(felicidadeMinima).construir();

        assertEquals(felicidadeMinima, pokemon.getFelicidade());
    }

    @Test
    public void deve_ter_felicidade_maxima_cem() throws NivelPokemonInvalidoException {
        int felicidadeMaxima = 100;

        Pokemon pokemon = new PokemonBuilder().comFelicidade(felicidadeMaxima).construir();

        assertEquals(felicidadeMaxima, pokemon.getFelicidade());
    }

    @Test
    public void nao_deve_ter_felicidade_menor_que_zero() {
        int felicidadeInvalida = -1;

        assertThrows(FelicidadeInvalidaException.class, () -> {
            new PokemonBuilder().comFelicidade(felicidadeInvalida).construir();
        });
    }

    @Test
    public void nao_deve_ter_felicidade_maior_que_cem() {
        int felicidadeInvalida = 101;

        assertThrows(FelicidadeInvalidaException.class, () -> {
            new PokemonBuilder().comFelicidade(felicidadeInvalida).construir();
        });
    }




}
