package br.com.digix.pokedigix.pokemon;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface PokemonRepository extends CrudRepository<Pokemon, Long> {
    
    Collection <Pokemon> findByNomeContaining(String nome);
}
