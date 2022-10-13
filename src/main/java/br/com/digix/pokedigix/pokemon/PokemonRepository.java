package br.com.digix.pokedigix.pokemon;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PokemonRepository extends PagingAndSortingRepository<Pokemon, Long> {
	Long deleteByNomeContaining(String nome);

	@Query("SELECT p FROM Pokemon p JOIN p.tipos t WHERE t.id = :tipoId") 
	Collection<Pokemon> buscarPorTipo(Long tipoId);

	Collection<Pokemon> findByNomeContaining(String nome);

	Page<Pokemon> findByNomeContaining(String nome, Pageable pageable);
}