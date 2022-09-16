package br.com.digix.pokedigix.tipo;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import br.com.digix.pokedigix.pokemon.Pokemon;
import br.com.digix.pokedigix.utils.EntidadeBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tipo extends EntidadeBase {

    @Column(nullable = false, length = 15)
    private String nome;

    @ManyToMany(mappedBy = "tipos")
    private Collection<Pokemon> pokemons;


    public Tipo(String nome) {
        this.nome = nome;
    }

    

}
