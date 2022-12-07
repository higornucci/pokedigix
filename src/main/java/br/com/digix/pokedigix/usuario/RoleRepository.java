package br.com.digix.pokedigix.usuario;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
    Optional<Role> findByName(ERole name);
}
