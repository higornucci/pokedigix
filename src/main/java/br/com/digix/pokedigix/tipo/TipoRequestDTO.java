package br.com.digix.pokedigix.tipo;

public class TipoRequestDTO {

    private String nome;

    public TipoRequestDTO(String nome) {
        this.nome = nome;
    }

    public TipoRequestDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}