package br.com.digix.pokedigix.personagem;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.digix.pokedigix.pokemon.Pokemon;

@Entity
public class Lider extends Personagem {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Insignia insignia;


    public Lider(String nome, Endereco endereco, Collection<Pokemon> pokemons, Insignia insignia) {
        super(nome, endereco);
        super.pokemons = pokemons;
        this.insignia = insignia;
    }
    public Lider(String nome, Endereco endereco, Pokemon pokemon, Insignia insignia) {
        super(nome, endereco);
        Collection<Pokemon> listaDePokemon = new ArrayList<>();
        listaDePokemon.add(pokemon);
        super.pokemons = listaDePokemon;
        this.insignia = insignia;
    }
    
    public Insignia getInsignia() {
        return insignia;
    }
    public void setInsignia(Insignia insignia) {
        this.insignia = insignia;
    }
    
}

