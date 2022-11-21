package br.com.digix.pokedigix.usuario;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository <Usuario, Long> {
    Optional<Usuario> findByUsername (String username);
    
    Boolean existsByUsername (String username);
    
    Boolean existsByEmail (String email);
}
