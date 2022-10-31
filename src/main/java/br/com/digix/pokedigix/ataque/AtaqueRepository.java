package br.com.digix.pokedigix.ataque;

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.digix.pokedigix.tipo.Tipo;

public interface AtaqueRepository extends PagingAndSortingRepository <Ataque, Long> {

	Collection<Ataque> findByTipo(Tipo tipo);
	Collection<Ataque> findByCategoria(Categoria categoria);
	
	@Cacheable("ataques")
	Collection<Ataque> findByNomeContaining(String nome);

	@Cacheable("buscarAtaques")
	Page<Ataque> findByNomeContaining(String nome, Pageable pageable);
}
