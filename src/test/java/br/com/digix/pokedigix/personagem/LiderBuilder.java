package br.com.digix.pokedigix.personagem;

import java.util.ArrayList;
import java.util.Collection;

import br.com.digix.pokedigix.pokemon.FelicidadeInvalidaException;
import br.com.digix.pokedigix.pokemon.LimiteDeAtaquePokemonException;
import br.com.digix.pokedigix.pokemon.LimiteDeTipoPokemonException;
import br.com.digix.pokedigix.pokemon.NivelPokemonInvalidoException;
import br.com.digix.pokedigix.pokemon.Pokemon;
import br.com.digix.pokedigix.pokemon.PokemonBuilder;

public class LiderBuilder {
    private String nome;
    private Endereco endereco;
    private Pokemon pokemon;
    private Insignia insignia;

    

    public LiderBuilder() throws NivelPokemonInvalidoException, FelicidadeInvalidaException, LimiteDeTipoPokemonException, LimiteDeAtaquePokemonException {
        this.nome = "Thor";
        this.endereco = new EnderecoBuilder().construir();
        this.pokemon = new PokemonBuilder().construir();
        this.insignia = Insignia.TROVAO;
    }

    public Lider construir() {
        Collection<Pokemon> listaDePokemon = new ArrayList<>();
        listaDePokemon.add(this.pokemon);
        return new Lider(nome, endereco, listaDePokemon, insignia);
    }

    public LiderBuilder comInsignia(Insignia insignia) {
        this.insignia = insignia;
        return this;
    }
    
    
}
