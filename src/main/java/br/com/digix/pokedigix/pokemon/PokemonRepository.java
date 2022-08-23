package br.com.digix.pokedigix.pokemon;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.digix.pokedigix.tipo.Tipo;

public interface PokemonRepository extends CrudRepository<Pokemon, Long> {

    @Query("SELECT p FROM Pokemon p JOIN p.tipos t WHERE t.id = :tipoId") //jpql
    Collection<Pokemon> buscarPorTipo(Long tipoId);


}
