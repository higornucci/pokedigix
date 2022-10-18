package br.com.digix.pokedigix.ataque;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.digix.pokedigix.tipo.Tipo;

public interface AtaqueRepository extends PagingAndSortingRepository<Ataque, Long> {

	Collection<Ataque> findByTipo(Tipo tipo);
	Collection<Ataque> findByCategoria(Categoria categoria);
	Collection<Ataque> findByNomeContaining(String nome);
	Page<Ataque> findByNomeContaining(String nome,Pageable pageable);
}
