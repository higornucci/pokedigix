package br.com.digix.pokedigix.tipo;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import br.com.digix.pokedigix.pokemon.Pokemon;

@Entity
public class Tipo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 15)
    private String nome;

    @ManyToMany(mappedBy = "tipos")
    private Collection<Pokemon> pokemons;

    public Tipo() {
    }

    public Tipo(String nomeEsperado) {
        this.nome = nomeEsperado;
    }

    public String getNome() {
        return this.nome;
    }

    public Long getId() {
        return this.id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Collection<Pokemon> getPokemons() {
        return pokemons;
    }

    public void setPokemons(Collection<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }

}
