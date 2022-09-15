package br.com.digix.pokedigix.pokemon;

public class LimiteDeTipoPokemonException extends Exception {
	public LimiteDeTipoPokemonException() {
		super("Um pokemon pode ter apenas 2 tipos.");
	}
}
