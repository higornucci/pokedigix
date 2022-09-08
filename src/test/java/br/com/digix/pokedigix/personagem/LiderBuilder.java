package br.com.digix.pokedigix.personagem;

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
        this.nome = "Ash";
        this.endereco = new EnderecoBuilder().construir();
        this.pokemon = new PokemonBuilder().construir();
        this.insignia = Insignia.ARCO_IRIS;
    }



    public Lider construir() {
        return new Lider(nome, endereco, pokemon, insignia);
    }

    public LiderBuilder comInsignia(Insignia insignia) {
        this.insignia = insignia;
        return this;
    }
    
    
}
