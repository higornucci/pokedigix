package br.com.digix.pokedigix.ataque;

public class AcuraciaInvalidaException extends Exception {

    public AcuraciaInvalidaException() {
        super("A acuracia deve estar entre 0 e 100.");
    }

}
