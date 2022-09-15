package br.com.digix.pokedigix.personagem;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TreinadorResponseDTO {

    private Long id;
    private String nome;
    private Endereco endereco;
    private int dinheiro;
    private int nivel;
    private Collection<Insignia> insignias;
}
