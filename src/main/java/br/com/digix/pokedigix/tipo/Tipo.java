package br.com.digix.pokedigix.tipo;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import br.com.digix.pokedigix.pokemon.Pokemon;
import br.com.digix.pokedigix.utils.EntidadeBase;

@Entity
public class Tipo extends EntidadeBase {

    @Column(nullable = false, length = 15)
    private String nome;

    @ManyToMany(mappedBy = "tipos")
    private Collection<Pokemon> pokemons;

    protected Tipo() {
    }

    public Tipo(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}
