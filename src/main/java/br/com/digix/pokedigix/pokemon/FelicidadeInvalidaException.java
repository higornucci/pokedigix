package br.com.digix.pokedigix.pokemon;

public class FelicidadeInvalidaException extends Exception {
    public FelicidadeInvalidaException() {
        super("A felicidade de um pokemon deve estar entre 0 e 100.");
    }
}
