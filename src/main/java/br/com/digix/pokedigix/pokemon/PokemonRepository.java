package br.com.digix.pokedigix.pokemon;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface PokemonRepository extends CrudRepository<Pokemon, Long> {
    Long deleteByNomeContaining(String nome);

    @Query("SELECT p FROM Pokemon p JOIN p.tipos t WHERE t.id = :tipoId") //jpql
    Collection<Pokemon> buscarPorTipo(Long tipoId);

    Collection <Pokemon> findByNomeContaining(String nome);
}
