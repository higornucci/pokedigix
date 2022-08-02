package br.com.digix.pokedigix.pokemon;

public class NivelPokemonInvalidoException extends Exception {
    public NivelPokemonInvalidoException() {
        super("O nivel do pokemon deve ficar entre 1 e 100.");
    }

}
