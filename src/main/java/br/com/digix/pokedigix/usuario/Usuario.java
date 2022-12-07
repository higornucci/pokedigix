package br.com.digix.pokedigix.usuario;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import br.com.digix.pokedigix.utils.EntidadeBase;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter @Setter
public class Usuario extends EntidadeBase {
    @Column(length = 20, nullable = false, unique = true)
    private String username;
    
    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 120, nullable = false)
    private String password;

    @ManyToMany
    @JoinTable(name = "usuario_role",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public Usuario(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

}
