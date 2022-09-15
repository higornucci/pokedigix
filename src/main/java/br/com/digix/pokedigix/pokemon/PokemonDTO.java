package br.com.digix.pokedigix.pokemon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class PokemonDTO {

    private String nome;
    private double altura;
    private double peso;
    private Genero genero;
    private int nivel;
    private int numeroPokedex;
    private int felicidade;
}
