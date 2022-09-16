package br.com.digix.pokedigix.personagem;

public class LimiteDePokemonException extends Exception {
	public LimiteDePokemonException() {
		super("Um treinador pode ter apenas 6 pokemons ao mesmo tempo.");
	}
}
