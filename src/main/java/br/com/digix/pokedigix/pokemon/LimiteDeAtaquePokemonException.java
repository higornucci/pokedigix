package br.com.digix.pokedigix.pokemon;

public class LimiteDeAtaquePokemonException extends Exception{
    public LimiteDeAtaquePokemonException() {
        super("Um pokemon pode ter apenas 4 ataques.");
    }
}
