package br.com.digix.pokedigix.tipo;

import java.util.Collection;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoRepository extends PagingAndSortingRepository<Tipo, Long> {

    Collection<Tipo> findByNomeContaining(String nome);
    Long deleteByNomeContaining(String nome);

    @Cacheable("tiposPaginados")
    Page<Tipo> findByNomeContaining(String nome, Pageable pageable);
}
