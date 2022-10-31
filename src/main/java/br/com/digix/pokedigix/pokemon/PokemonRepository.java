package br.com.digix.pokedigix.pokemon;

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PokemonRepository extends PagingAndSortingRepository<Pokemon, Long> {
	Long deleteByNomeContaining(String nome);

	@Query("SELECT p FROM Pokemon p JOIN p.tipos t WHERE t.id = :tipoId") // jpql
	Collection<Pokemon> buscarPorTipo(Long tipoId);

	@Cacheable ("pokemonsPaginados")
	Collection<Pokemon> findByNomeContaining(String nome);

	@Cacheable("pokemonsPaginados")
	Page<Pokemon> findByNomeContaining(String nome, Pageable pageable);
}
