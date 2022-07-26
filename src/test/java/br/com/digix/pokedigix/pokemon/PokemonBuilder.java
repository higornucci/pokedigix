package br.com.digix.pokedigix.pokemon;

import java.util.ArrayList;
import java.util.Collection;

import br.com.digix.pokedigix.ataque.Ataque;
import br.com.digix.pokedigix.ataque.AtaqueBuilder;
import br.com.digix.pokedigix.tipo.Tipo;

public class PokemonBuilder {

    private String nome;
    private double altura;
    private double peso;
    private Genero genero;
    private int nivel;
    private int numeroPokedex;
    private int felicidade;
    private Collection<Tipo> tipos;
    private Collection<Ataque> ataques;

    public PokemonBuilder() {
        this.nome = "Gastly";
        this.altura = 0.8;
        this.peso = 0.1;
        this.genero = Genero.MASCULINO;
        this.nivel = 5;
        this.numeroPokedex = 92;
        this.felicidade = 0;
        this.tipos = new ArrayList<>();
        this.ataques = new ArrayList<>();
    }

    public PokemonBuilder comAtaque(Ataque ataque) {
        this.ataques.add(ataque);
        return this;
    }
    
    public PokemonBuilder comTipo(Tipo tipo) {
        this.tipos.add(tipo);
        return this;
    }

    public Pokemon construir() {
        return new Pokemon(nome, 
        altura, peso, genero, nivel, 
        numeroPokedex, felicidade, tipos, ataques);
    }
}
