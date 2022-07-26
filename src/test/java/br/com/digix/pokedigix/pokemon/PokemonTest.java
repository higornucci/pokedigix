package br.com.digix.pokedigix.pokemon;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

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

        Pokemon gastly = new Pokemon(nome, 
            altura, 
            peso, 
            genero, 
            nivel, 
            numeroPokedex, 
            felicidade, 
            tipos);

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
        String nome = "Gastly";
        double altura = 0.8;
        double peso = 0.1;
        Genero genero = Genero.MASCULINO;
        int nivel = 5;
        int numeroPokedex = 92;
        int felicidade = 0;
        List<Tipo> tipos = new ArrayList<>();
        tipos.add(new Tipo("Fantasma"));

        Pokemon gastly = new Pokemon(nome, altura, peso, genero, nivel, numeroPokedex, felicidade, tipos);

        assertEquals(tipos, gastly.getTipos());        
    }
}
