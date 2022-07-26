package br.com.digix.pokedigix.pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.Test;

import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.ataque.AtaqueBuilder;
import br.com.digix.pokedigix.tipo.Tipo;

public class PokemonTest {
    @Test
    public void deve_criar_um_pokemon() {
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
    public void deve_cadastrar_um_tipo_para_o_pokemon() {
        Tipo tipo = new Tipo("Fantasma");

        Pokemon gastly = new PokemonBuilder().comTipo(tipo).construir();

        assertTrue(gastly.getTipos().contains(tipo));        
    }
}
