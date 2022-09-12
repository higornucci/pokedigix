package br.com.digix.pokedigix.personagem;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import br.com.digix.pokedigix.pokemon.Pokemon;
import br.com.digix.pokedigix.utils.EntidadeBase;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Personagem extends EntidadeBase {

    @Column(nullable = false)
    private String nome;

    @ManyToOne
    private Endereco endereco;

    @OneToMany (mappedBy = "treinador")
    protected Collection<Pokemon> pokemons;

    protected Personagem() {
    }

    protected Personagem(String nome, Endereco endereco) {
        this.nome = nome;
        this.endereco = endereco;
        this.pokemons = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Collection<Pokemon> getPokemons() {
        return pokemons;
    }

}
