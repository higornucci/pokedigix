package br.com.digix.pokedigix.tipo;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface TipoRepository extends CrudRepository<Tipo, Long> {

    Collection<Tipo> findByNome(String nome);
    
}
