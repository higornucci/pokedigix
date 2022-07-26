package br.com.digix.pokedigix.pokemon;

import org.springframework.data.repository.CrudRepository;

public interface PokemonRepository extends CrudRepository<Pokemon, Long> {
    
}
