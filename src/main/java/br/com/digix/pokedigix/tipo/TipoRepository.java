package br.com.digix.pokedigix.tipo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TipoRepository extends CrudRepository<Tipo, Long> {
    List<Tipo> findByNome(String nome);
}
