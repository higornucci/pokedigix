package br.com.digix.pokedigix.personagem;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.com.digix.pokedigix.pokemon.Pokemon;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Lider extends Personagem {

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Insignia insignia;

	

    public Lider(String nome, Endereco endereco, Collection<Pokemon> pokemons, Insignia insignia) {
        super(nome, endereco);
        super.pokemons = pokemons;
        this.insignia = insignia;
    }
    
    
    
}
