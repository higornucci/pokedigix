package br.com.digix.pokedigix.personagem;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

public interface EnderecoRepository extends CrudRepository<Endereco, Long> {

    
    Collection <Endereco> findByCidadeContaining (String cidade);


}
