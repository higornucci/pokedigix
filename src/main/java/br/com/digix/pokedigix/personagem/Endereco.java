package br.com.digix.pokedigix.personagem;

import javax.persistence.Column;
import javax.persistence.Entity;

import br.com.digix.pokedigix.utils.EntidadeBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Endereco extends EntidadeBase {

    @Column(nullable = false)
    private String regiao;
    @Column(nullable = false)
    private String cidade;

}
